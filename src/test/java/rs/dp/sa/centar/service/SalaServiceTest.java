package rs.dp.sa.centar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.dp.sa.centar.dto.SalaRequest;
import rs.dp.sa.centar.dto.SalaResponse;
import rs.dp.sa.centar.entity.Sala;
import rs.dp.sa.centar.entity.SportskiCentar;
import rs.dp.sa.centar.repository.SalaRepository;
import rs.dp.sa.centar.repository.SportskiCentarRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository sRepository;

    @Mock
    private SportskiCentarRepository scRepository;

    @InjectMocks
    private SalaService sService;

    private Sala mockSala;
    private SportskiCentar mockCentar;

    @BeforeEach
    void setUp() {

        mockCentar = new SportskiCentar();
        mockCentar.setSportskiCentarId(10L);

        mockSala = new Sala();
        mockSala.setSalaId(1L);
        mockSala.setNaziv("Velika Sala");
        mockSala.setCenaPoSatu(2500.0);
        mockSala.setNaOtvorenom("NE");
        mockSala.setSportskiCentar(mockCentar);

    }

    @Test
    @DisplayName("Uspesno kreiranje nove sale u postojecem sportskom centru")
    void create_Success(){

        when(sRepository.existsByNazivAndSportskiCentarSportskiCentarId("Velika Sala", 10L)).thenReturn(false);
        when(scRepository.findById(10L)).thenReturn(Optional.of(mockCentar));
        when(sRepository.save(any(Sala.class))).thenReturn(mockSala);

        SalaRequest request = new SalaRequest("Velika Sala", "NE", 2500.00, 10L);
        SalaResponse response = sService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.salaId());
        assertEquals("Velika Sala", response.naziv());


        verify(sRepository, times(1)).existsByNazivAndSportskiCentarSportskiCentarId("Velika Sala", 10L);
        verify(scRepository, times(1)).findById(10L);
        verify(sRepository, times(1)).save(any(Sala.class));

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada sala sa istim nazivom vec postoji u tom sportskom centru")
    void create_SalaAlreadyExists_ThrowsException() {

        when(sRepository.existsByNazivAndSportskiCentarSportskiCentarId("Velika Sala", 10L)).thenReturn(true);

        SalaRequest request = new SalaRequest("Velika Sala", "NE", 2500.0, 10L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sService.create(request));

        String msg = exception.getMessage();
        assertEquals("Sala vec postoji u ovom sportskom centru", msg);

        verify(sRepository, times(1)).existsByNazivAndSportskiCentarSportskiCentarId("Velika Sala", 10L);
        verify(scRepository, never()).findById(anyLong());
        verify(sRepository, never()).save(any(Sala.class));

    }

    @Test
    @DisplayName("Bacanje RuntimeException-a kada prosledjeni sportski centar ne postoji u bazi")
    void create_SportskiCentarNotFound_ThrowsException() {

        when(sRepository.existsByNazivAndSportskiCentarSportskiCentarId("Velika Sala", 10L)).thenReturn(false);
        when(scRepository.findById(10L)).thenReturn(Optional.empty());

        SalaRequest request = new SalaRequest("Velika Sala", "NE", 2500.0, 10L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sService.create(request));

        String msg = exception.getMessage();
        assertEquals("Sportski centar ne postoji", msg);

        verify(sRepository).existsByNazivAndSportskiCentarSportskiCentarId("Velika Sala", 10L);
        verify(scRepository).findById(10L);
        verify(sRepository, never()).save(any(Sala.class));


    }

    @Test
    @DisplayName("Uspesno vracanje liste svih sala iz baze mapiranih u DTO objekte")
    void findAll_Success() {

        when(sRepository.findAll()).thenReturn(List.of(mockSala));

        List<SalaResponse> response = sService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Velika Sala", response.get(0).naziv());


        verify(sRepository).findAll();


    }
}