package rs.dp.sa.centar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.dp.sa.centar.dto.TrenerRequest;
import rs.dp.sa.centar.dto.TrenerResponse;
import rs.dp.sa.centar.entity.Sala;
import rs.dp.sa.centar.entity.Trener;
import rs.dp.sa.centar.repository.SalaRepository;
import rs.dp.sa.centar.repository.TrenerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrenerServiceTest {

    @Mock
    private TrenerRepository tRepository;

    @Mock
    private SalaRepository sRepository;

    @InjectMocks
    private TrenerService tService;

    private Trener mockTrener;
    private Sala mockSala;


    @BeforeEach
    void setUp() {
        mockSala = new Sala();
        mockSala.setSalaId(5L);
        mockSala.setNaziv("Sala 1");

        mockTrener = new Trener();
        mockTrener.setTrenerId(1L);
        mockTrener.setIme("Marko");
        mockTrener.setPrezime("Markovic");
        mockTrener.setTelefon("064111222");
        mockTrener.setSala(mockSala);
    }

    @Test
    @DisplayName("Uspesno kreiranje novog trenera i dodeljivanje sale")
    void create_Success() {

        when(sRepository.findById(5L)).thenReturn(Optional.of(mockSala));
        when(tRepository.save(any(Trener.class))).thenReturn(mockTrener);

        TrenerRequest request = new TrenerRequest("Marko", "Markovic", "064111222", 5L);
        TrenerResponse response = tService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.trenerId());


        verify(sRepository, times(1)).findById(5L);
        verify(tRepository, times(1)).save(any(Trener.class));

    }


    @Test
    @DisplayName("Bacanje RuntimeException-a pri kreiranju ako prosledjena sala ne postoji")
    void create_SalaNotFound_ThrowsException() {
        when(sRepository.findById(5L)).thenReturn(Optional.empty());

        TrenerRequest request = new TrenerRequest("Marko", "Markovic", "064111222", 5L);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                tService.create(request)
        );

        String msg = exception.getMessage();
        assertEquals("Sala sa prosledjenim ID-jem ne postoji u bazi", msg);

        verify(sRepository).findById(5L);
        verify(tRepository, never()).save(any(Trener.class));
    }

    @Test
    @DisplayName("Uspesna izmena podataka i sale postojeceg trenera")
    void update_Success() {

        when(tRepository.findById(1L)).thenReturn(Optional.of(mockTrener));
        when(sRepository.findById(5L)).thenReturn(Optional.of(mockSala));
        when(tRepository.save(any(Trener.class))).thenReturn(mockTrener);

        TrenerRequest request = new TrenerRequest("Dusan", "Markovic", "123456", 5L);

        mockTrener.setIme("Dusan");
        mockTrener.setTelefon("123456");

        TrenerResponse response = tService.update(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.trenerId());

        verify(tRepository).findById(1L);
        verify(sRepository).findById(5L);
        verify(tRepository).save(any(Trener.class));


    }

    @Test
    @DisplayName("Bacanje RuntimeException-a pri izmeni ako trener ne postoji")
    void update_TrenerNotFound_ThrowsException() {
        when(tRepository.findById(1L)).thenReturn(Optional.empty());

        TrenerRequest request = new TrenerRequest("Marko", "Markovic", "064111222", 5L);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                tService.update(1L, request)
        );

        String msg = exception.getMessage();
        assertEquals("Trener sa prosledjenim ID-jem ne postoji u bazi", msg);

        verify(tRepository, times(1)).findById(1L);
        verify(sRepository, never()).findById(anyLong());
        verify(tRepository, never()).save(any(Trener.class));
    }

    @Test
    @DisplayName("Bacanje RuntimeException-a pri izmeni ako nova sala ne postoji")
    void update_SalaNotFound_ThrowsException() {
        when(tRepository.findById(1L)).thenReturn(Optional.of(mockTrener));
        when(sRepository.findById(5L)).thenReturn(Optional.empty());

        TrenerRequest request = new TrenerRequest("Marko", "Markovic", "064111222", 5L);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                tService.update(1L, request)
        );

        String msg = exception.getMessage();
        assertEquals("Promenjena sala sa ID-jem ne postoji", msg);

        verify(tRepository, times(1)).findById(1L);
        verify(sRepository, times(1)).findById(5L);
        verify(tRepository, never()).save(any(Trener.class));
    }

    @Test
    @DisplayName("Uspesno brisanje trenera iz baze")
    void delete_Success(){

        when(tRepository.findById(1L)).thenReturn(Optional.of(mockTrener));

        assertDoesNotThrow( () -> tService.delete(1L));

        verify(tRepository, times(1)).findById(1L);
        verify(tRepository, times(1)).delete(mockTrener);

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a pri brisanju ako trener ne postoji")
    void delete_TrenerNotFound_ThrowsException() {
        when(tRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                tService.delete(1L)
        );

        String msg = exception.getMessage();
        assertEquals("Prosledjeni trener sa ID-jem ne postoji", msg);

        verify(tRepository).findById(1L);
        verify(tRepository, never()).delete(any(Trener.class));
    }

    @Test
    @DisplayName("Uspesno pronalazenje trenera preko njegovog ID-a")
    void findById_Success(){

        when(tRepository.findById(1L)).thenReturn(Optional.of(mockTrener));

        TrenerResponse response = tService.findById(1L);

        assertNotNull(response);

        verify(tRepository).findById(1L);

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada se trazi trener koji ne postoji")
    void findById_NotFound_ThrowsException(){

        when(tRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tService.findById(1L));

        String msg = exception.getMessage();
        assertEquals("Nema trenera sa tim ID-jem u bazi", msg);

        verify(tRepository).findById(1L);

    }




}