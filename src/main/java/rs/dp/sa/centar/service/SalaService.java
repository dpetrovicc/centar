package rs.dp.sa.centar.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dp.sa.centar.dto.SalaRequest;
import rs.dp.sa.centar.dto.SalaResponse;
import rs.dp.sa.centar.entity.Sala;
import rs.dp.sa.centar.entity.SportskiCentar;
import rs.dp.sa.centar.repository.SalaRepository;
import rs.dp.sa.centar.repository.SportskiCentarRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SalaService {

    private final SalaRepository sRepository;
    private final SportskiCentarRepository scRepository;

    @Transactional
    public SalaResponse create(SalaRequest request){
        if(sRepository.existsByNazivAndSportskiCentarSportskiCentarId(request.naziv(), request.sportskiCentarId())){
            throw new RuntimeException("Sala vec postoji u ovom sportskom centru");
        }

        SportskiCentar sc = scRepository.findById(request.sportskiCentarId()).
                orElseThrow(() -> new RuntimeException("Sportski centar ne postoji"));

        Sala s = new Sala();
        s.setNaziv(request.naziv());
        s.setCenaPoSatu(request.cenaPoSatu());
        s.setNaOtvorenom(request.naOtvorenom());
        s.setSportskiCentar(sc);

        Sala nova = sRepository.save(s);

        return new SalaResponse(
                nova.getSalaId(),
                nova.getNaziv(),
                nova.getNaOtvorenom(),
                nova.getCenaPoSatu(),
                nova.getSportskiCentar().getSportskiCentarId()
        );
    }

    @Transactional(readOnly = true)
    public List<SalaResponse> findAll(){
        return sRepository.findAll().stream()
                .map(sala -> new SalaResponse(
                        sala.getSalaId(),
                        sala.getNaziv(),
                        sala.getNaOtvorenom(),
                        sala.getCenaPoSatu(),
                        sala.getSportskiCentar().getSportskiCentarId()
                )).collect(Collectors.toList());
    }


}
