package rs.dp.sa.centar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.dp.sa.centar.dto.RacunRequest;
import rs.dp.sa.centar.dto.RacunResponse;
import rs.dp.sa.centar.entity.Racun;
import rs.dp.sa.centar.entity.RezervacijaTermina;
import rs.dp.sa.centar.repository.RacunRepository;
import rs.dp.sa.centar.repository.RezervacijaTerminaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RacunServiceTest {

    @Mock
    private RacunRepository rRepository;
    @Mock
    private RezervacijaTerminaRepository rtRepository;
    @InjectMocks
    private RacunService rService;

    private Racun mockRacun;
    private RezervacijaTermina mockRezervacija;

    @BeforeEach
    void setUp() {
        mockRezervacija = new RezervacijaTermina();
        mockRezervacija.setRezervacijaId(100L);
        mockRezervacija.setUkupnaCena(4500.0);


        mockRacun = new Racun();
        mockRacun.setRacunId(1L);
        mockRacun.setRezervacijaTermina(mockRezervacija);
        mockRacun.setDatumIzdavanja(LocalDate.now());
        mockRacun.setStatusPlacanja("Nije placeno");
        mockRacun.setUkupanIznos(4500.0);
    }

    @Test
    @DisplayName("Uspesno kreiranje racuna na osnovu postojece rezervacije")
    void create_Success(){
        when(rtRepository.findById(100L)).thenReturn(Optional.of(mockRezervacija));
        when(rRepository.save(any(Racun.class))).thenReturn(mockRacun);

        RacunRequest request = new RacunRequest(100L);
        RacunResponse response = rService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.racunId());
        assertEquals(LocalDate.now(), response.datumIzdavanja());
        assertEquals(100L, response.rezervacijaId());


        verify(rtRepository).findById(100L);
        verify(rRepository).save(any(Racun.class));

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada rezervacija sa prosledjenim ID-em ne postoji za racun")
    void create_RezervacijaNotFound_ThrowsException(){
       when(rtRepository.findById(100L)).thenReturn(Optional.empty());

       RacunRequest request = new RacunRequest(100L);
       RuntimeException exception = assertThrows(RuntimeException.class, () -> rService.create(request));

       String msg = exception.getMessage();
       assertEquals("Rezervacija sa ovim ID-em ne postoji", msg);

       verify(rtRepository).findById(100L);
       verify(rRepository, never()).save(any(Racun.class));


    }
}