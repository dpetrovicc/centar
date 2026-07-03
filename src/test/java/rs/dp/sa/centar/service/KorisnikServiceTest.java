package rs.dp.sa.centar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.dp.sa.centar.dto.KorisnikRequest;
import rs.dp.sa.centar.dto.KorisnikResponse;
import rs.dp.sa.centar.entity.Korisnik;
import rs.dp.sa.centar.repository.KorisnikRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KorisnikServiceTest {

    @Mock
    private KorisnikRepository korisnikRepository;

    @InjectMocks
    private KorisnikService korisnikService;

    private Korisnik mockKorisnik;


    @BeforeEach
    void setUp() {
        mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnikId(1L);
        mockKorisnik.setIme("Nikola");
        mockKorisnik.setPrezime("Nikolic");
        mockKorisnik.setEmail("nikola@gmail.com");
        mockKorisnik.setTelefon("065111222");
    }

    @Test
    @DisplayName("Uspesno kreiranje korisnika kada je email jedinstven")
    void create_Success() {
        when(korisnikRepository.findByEmail("nikola@gmail.com")).thenReturn(Optional.empty());
        when(korisnikRepository.save(any(Korisnik.class))).thenReturn(mockKorisnik);

        KorisnikRequest request = new KorisnikRequest("Nikola", "Nikolic", "nikola@gmail.com", "065111222");
        KorisnikResponse response = korisnikService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.korisnikId());
        assertEquals("Nikola", response.ime());
        assertEquals("nikola@gmail.com", response.email());

        verify(korisnikRepository).findByEmail("nikola@gmail.com");
        verify(korisnikRepository).save(any(Korisnik.class));

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada se pokusa kreiranje sa postojecim emailom")
    void create_EmailAlreadyExists_ThrowsException() {

        when(korisnikRepository.findByEmail("nikola@gmail.com")).thenReturn(Optional.of(mockKorisnik));

        KorisnikRequest request = new KorisnikRequest("Nikola", "Nikolic", "nikola@gmail.com", "065111222");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> korisnikService.create(request));

        String msg = exception.getMessage();
        assertEquals("Ovaj email vec postoji", msg);

        verify(korisnikRepository, never()).save(any(Korisnik.class));
    }


    @Test
    @DisplayName("Uspesno vracanje liste svih korisnika mapiranih u DTO objekte")
    void findAll_Success() {

        when(korisnikRepository.findAll()).thenReturn(List.of(mockKorisnik));

        List<KorisnikResponse> response = korisnikService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Nikola", response.get(0).ime());

        verify(korisnikRepository).findAll();


    }
}