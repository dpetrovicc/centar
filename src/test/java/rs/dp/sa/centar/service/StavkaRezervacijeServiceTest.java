package rs.dp.sa.centar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.dp.sa.centar.dto.StavkaRezervacijeRequest;
import rs.dp.sa.centar.dto.StavkaRezervacijeResponse;
import rs.dp.sa.centar.entity.RezervacijaTermina;
import rs.dp.sa.centar.entity.Sala;
import rs.dp.sa.centar.entity.StavkaRezervacije;
import rs.dp.sa.centar.repository.RezervacijaTerminaRepository;
import rs.dp.sa.centar.repository.SalaRepository;
import rs.dp.sa.centar.repository.StavkaRezervacijeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StavkaRezervacijeServiceTest {

    @Mock
    private StavkaRezervacijeRepository srRepository;

    @Mock
    private RezervacijaTerminaRepository  rtRepository;

    @Mock
    private SalaRepository sRepository;

    @InjectMocks
    private StavkaRezervacijeService srService;

    private RezervacijaTermina mockRezervacija;
    private StavkaRezervacije mockStavka;
    private Sala mockSala;


    @BeforeEach
    void setUp() {

        mockRezervacija = new RezervacijaTermina();
        mockRezervacija.setRezervacijaId(100L);
        mockRezervacija.setUkupnaCena(1000.0);

        mockSala = new Sala();
        mockSala.setSalaId(5L);
        mockSala.setCenaPoSatu(1500.0);

        mockStavka = new StavkaRezervacije();
        mockStavka.setStavkaId(500L);
        mockStavka.setRezervacijaTermina(mockRezervacija);
        mockStavka.setSala(mockSala);
        mockStavka.setBrojSati(2);
        mockStavka.setCena(3000.0);
        mockStavka.setNapomena("Trening kosarka");
    }

    @Test
    @DisplayName("Uspesno kreiranje stavke rezervacije, računanje cene i ažuriranje ukupne cene rezervacije")
    void create_Success(){
        when(rtRepository.findById(100L)).thenReturn(Optional.of(mockRezervacija));
        when(sRepository.findById(5L)).thenReturn(Optional.of(mockSala));
        when(srRepository.save(any(StavkaRezervacije.class))).thenReturn(mockStavka);
        when(rtRepository.save(any(RezervacijaTermina.class))).thenReturn(mockRezervacija);

        StavkaRezervacijeRequest request = new StavkaRezervacijeRequest(100L, 5L, 2, "Trening kosarka");
        StavkaRezervacijeResponse response = srService.create(request);

        assertNotNull(response);
        assertEquals(500L, response.stavkaId());
        assertEquals(100L, response.rezervacijaId());
        assertEquals(5L, response.salaId());
        assertEquals(3000.0, response.cena());
        assertEquals("Trening kosarka", response.napomena());
        assertEquals(2, response.brojSati());

        assertEquals(4000.0, mockRezervacija.getUkupnaCena());

        verify(rtRepository).findById(100L);
        verify(sRepository).findById(5L);
        verify(srRepository).save(any(StavkaRezervacije.class));
        verify(rtRepository).save(any(RezervacijaTermina.class));


    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada rezervacija sa prosledjenim ID-em ne postoji")
    void create_RezervacijaNotFound_ThrowsException(){

        when(rtRepository.findById(100L)).thenReturn(Optional.empty());

        StavkaRezervacijeRequest request = new StavkaRezervacijeRequest(100L, 5L, 2, "Napomena");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> srService.create(request));

        String msg = exception.getMessage();
        assertEquals("Nema prosledjenog ID-a Rezervacije u bazi", msg);

        verify(rtRepository).findById(100L);
        verify(sRepository, never()).findById(anyLong());
        verify(srRepository, never()).save(any(StavkaRezervacije.class));
        verify(rtRepository, never()).save(any(RezervacijaTermina.class));


    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada sala sa prosledjenim ID-em ne postoji")
    void create_SalaNotFound_ThrowsException() {
        when(rtRepository.findById(100L)).thenReturn(Optional.of(mockRezervacija));
        when(sRepository.findById(5L)).thenReturn(Optional.empty());

        StavkaRezervacijeRequest request = new StavkaRezervacijeRequest(100L, 5L, 2, "Napomena");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> srService.create(request));

        assertEquals("Nema prosledjenog ID-a Sale bazi", exception.getMessage());

        verify(rtRepository).findById(100L);
        verify(sRepository).findById(5L);
        verify(srRepository, never()).save(any(StavkaRezervacije.class));
        verify(rtRepository, never()).save(any(RezervacijaTermina.class));
    }
}