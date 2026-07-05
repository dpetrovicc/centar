package rs.dp.sa.centar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.dp.sa.centar.dto.ClanskaKartaRequest;
import rs.dp.sa.centar.dto.ClanskaKartaResponse;
import rs.dp.sa.centar.entity.ClanskaKarta;
import rs.dp.sa.centar.entity.Korisnik;
import rs.dp.sa.centar.repository.ClanskaKartaRepository;
import rs.dp.sa.centar.repository.KorisnikRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClanskaKartaServiceTest {

    @Mock
    private ClanskaKartaRepository ckRepository;

    @Mock
    private KorisnikRepository kRepository;

    @InjectMocks
    private ClanskaKartaService ckService;

    private ClanskaKarta mockClanskaKarta;
    private Korisnik mockKorisnik;

    @BeforeEach
    void setUp() {
        mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnikId(1L);
        mockKorisnik.setIme("Nikola");
        mockKorisnik.setPrezime("Nikolic");

        mockClanskaKarta = new ClanskaKarta();
        mockClanskaKarta.setKartaId(100L);
        mockClanskaKarta.setKorisnik(mockKorisnik);
        mockClanskaKarta.setDatumAktivacije(LocalDate.now());
        mockClanskaKarta.setVaziDo(LocalDate.now().plusMonths(1));
    }

    @Test
    @DisplayName("Uspesno kreiranje i aktivacija clanske karte na mesec dana")
    void create_Success() {

        when(kRepository.findById(1L)).thenReturn(Optional.of(mockKorisnik));
        when(ckRepository.existsByKorisnikKorisnikId(1L)).thenReturn(false);
        when(ckRepository.save(any(ClanskaKarta.class))).thenReturn(mockClanskaKarta);

        ClanskaKartaRequest request = new ClanskaKartaRequest(1L);
        ClanskaKartaResponse response = ckService.create(request);

        assertNotNull(response);
        assertEquals(100L, response.kartaId());


        verify(kRepository).findById(1L);
        verify(ckRepository).existsByKorisnikKorisnikId(1L);
        verify(ckRepository).save(any(ClanskaKarta.class));

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada korisnik sa prosledjenim ID-em ne postoji")
    void create_KorisnikNotFound_ThrowsException() {

        when(kRepository.findById(1L)).thenReturn(Optional.empty());

        ClanskaKartaRequest request = new ClanskaKartaRequest(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ckService.create(request));

        String msg = exception.getMessage();
        assertEquals("Korisnik ne postoji", msg);

        verify(kRepository, times(1)).findById(1L);
        verify(ckRepository, never()).existsByKorisnikKorisnikId(anyLong());
        verify(ckRepository, never()).save(any(ClanskaKarta.class));


    }


    @Test
    @DisplayName("Bacanje RuntimeException-a kada korisnik vec poseduje aktivnu clansku kartu")
    void create_ClanskaKartaAlreadyExists_ThrowsException() {

        when(kRepository.findById(1L)).thenReturn(Optional.of(mockKorisnik));
        when(ckRepository.existsByKorisnikKorisnikId(anyLong())).thenReturn(true);

        ClanskaKartaRequest request = new ClanskaKartaRequest(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ckService.create(request));

        String msg = exception.getMessage();
        assertEquals("Korisnik vec ima clansku kartu", msg);

        verify(kRepository, times(1)).findById(1L);
        verify(ckRepository).existsByKorisnikKorisnikId(1L);
        verify(ckRepository, never()).save(any(ClanskaKarta.class));


    }

}