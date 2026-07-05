package rs.dp.sa.centar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.dp.sa.centar.dto.RezervacijaTerminaRequest;
import rs.dp.sa.centar.dto.RezervacijaTerminaResponse;
import rs.dp.sa.centar.entity.Korisnik;
import rs.dp.sa.centar.entity.RezervacijaTermina;
import rs.dp.sa.centar.entity.SportskiCentar;
import rs.dp.sa.centar.repository.KorisnikRepository;
import rs.dp.sa.centar.repository.RezervacijaTerminaRepository;
import rs.dp.sa.centar.repository.SportskiCentarRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RezervacijaTerminaServiceTest {

    @Mock
    private RezervacijaTerminaRepository rtRepository;

    @Mock
    private KorisnikRepository kRepository;

    @Mock
    private SportskiCentarRepository scRepository;

    @InjectMocks
    private  RezervacijaTerminaService rtService;

    private Korisnik mockKorisnik;
    private SportskiCentar mockCentar;
    private RezervacijaTermina mockRezervacija;
    private Date testDatum;

    @BeforeEach
    void setUp() {
        mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnikId(1L);

        mockCentar = new SportskiCentar();
        mockCentar.setSportskiCentarId(10L);

        testDatum = new Date();

        mockRezervacija = new RezervacijaTermina();
        mockRezervacija.setRezervacijaId(100L);
        mockRezervacija.setDatum(testDatum);
        mockRezervacija.setKorisnik(mockKorisnik);
        mockRezervacija.setSportskiCentar(mockCentar);
        mockRezervacija.setOdobreno("NE");
        mockRezervacija.setUkupnaCena(0.0);
    }

    @Test
    @DisplayName("Uspesno kreiranje rezervacije termina sa pocetnom cenom 0.0 i statusom NE")
    void create_Success() {
        when(kRepository.findById(1L)).thenReturn(Optional.of(mockKorisnik));
        when(scRepository.findById(10L)).thenReturn(Optional.of(mockCentar));
        when(rtRepository.save(any(RezervacijaTermina.class))).thenReturn(mockRezervacija);


        RezervacijaTerminaRequest request = new RezervacijaTerminaRequest(10L, 1L, testDatum);
        RezervacijaTerminaResponse response = rtService.create(request);

        assertNotNull(response);
        assertEquals(100L, response.rezervacijaId());
        assertEquals(0.0, response.ukupnaCena());
        assertEquals("NE", response.odobreno());
        assertEquals(testDatum, response.datum());

        verify(kRepository, times(1)).findById(1L);
        verify(scRepository, times(1)).findById(10L);
        verify(rtRepository, times(1)).save(any(RezervacijaTermina.class));

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a pri kreiranju ako korisnik ne postoji u bazi")
    void create_KorisnikNotFound_ThrowsException() {
        when(kRepository.findById(1L)).thenReturn(Optional.empty());

        RezervacijaTerminaRequest request = new RezervacijaTerminaRequest(10L, 1L, testDatum);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                rtService.create(request)
        );

        assertEquals("Nema korisnika sa tim ID-em u bazi", exception.getMessage());

        verify(kRepository).findById(1L);
        verify(scRepository, never()).findById(anyLong());
        verify(rtRepository, never()).save(any(RezervacijaTermina.class));
    }

    @Test
    @DisplayName("Bacanje RuntimeException-a pri kreiranju ako sportski centar ne postoji u bazi")
    void create_SportskiCentarNotFound_ThrowsException() {
        when(kRepository.findById(1L)).thenReturn(Optional.of(mockKorisnik));
        when(scRepository.findById(10L)).thenReturn(Optional.empty());

        RezervacijaTerminaRequest request = new RezervacijaTerminaRequest(10L, 1L, testDatum);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> rtService.create(request));

        assertEquals("Nema sportskog centra sa tim ID-em u bazi", exception.getMessage());

        verify(kRepository).findById(1L);
        verify(scRepository).findById(10L);
        verify(rtRepository, never()).save(any(RezervacijaTermina.class));
    }


    @Test
    @DisplayName("Uspesno pronalazenje rezervacije preko njenog ID-a")
    void findById_Success() {
        when(rtRepository.findById(100L)).thenReturn(Optional.of(mockRezervacija));

        RezervacijaTerminaResponse response = rtService.findById(100L);

        assertNotNull(response);
        assertEquals(100L, response.rezervacijaId());
        assertEquals(testDatum, response.datum());

        verify(rtRepository).findById(100L);
    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada se trazi rezervacija koja ne postoji")
    void findById_NotFound_ThrowsException(){

        when(rtRepository.findById(100L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> rtService.findById(100L));

        String msg = exception.getMessage();
        assertEquals("Nema rezervacije sa unetim ID-em", msg);

        verify(rtRepository).findById(100L);

    }

    @Test
    @DisplayName("Uspesno otkazivanje rezervacije i promena statusa u OTKAZANO")
    void cancel_Success() {

        when(rtRepository.findById(100L)).thenReturn(Optional.of(mockRezervacija));
        when(rtRepository.save(any(RezervacijaTermina.class))).thenReturn(mockRezervacija);

        mockRezervacija.setOdobreno("OTKAZANO");

        RezervacijaTerminaResponse response = rtService.cancel(100L);

        assertNotNull(response);
        assertEquals(100L, response.rezervacijaId());
        assertEquals("OTKAZANO", response.odobreno());

        verify(rtRepository).findById(100L);
        verify(rtRepository).save(any(RezervacijaTermina.class));

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a pri otkazivanju ako rezervacija ne postoji")
    void cancel_NotFound_ThrowsException(){
        when(rtRepository.findById(100L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> rtService.cancel(100L));

        String msg = exception.getMessage();
        assertEquals("Ne posotoji rezervacija sa ovim ID-em u bazi", msg);

        verify(rtRepository).findById(100L);
        verify(rtRepository, never()).save(any(RezervacijaTermina.class));

    }

}