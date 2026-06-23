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

/**
 * Servis klasa za upravljanje salama
 * Sadrzi logiku za kreiranje nove sale i pronalazenje svih dostupnih sala iz baze
 * @author Dusan Petrovic
 */
@Slf4j
@Service
@AllArgsConstructor
public class SalaService {

    /**
     * Repozitorijum za rad sa podacima o salama u bazi podataka
     */
    private final SalaRepository sRepository;
    /**
     * Repozitorijum za rad sa podacima o sportskim centrima u bazi podataka
     */
    private final SportskiCentarRepository scRepository;

    /**
     * Kreira novu salu u bazi u odredjenom sportskom centru
     * Proverava se da li sala sa prosledjenim nazivom vec postoji u sportskom centru
     * Proverava se da li prosledjeni sportski centar postoji u bazi
     *
     * @param request DTO objekat koji sadrzi naziv, cenu po satu, da li je na otvorenom i ID sportskog centra
     * @return SalaResponse DTO objekat sa podacima o kreiranoj sali i geneirsanim ID-em
     * @throws java.lang.RuntimeException ukoliko sala sa prosledjenim nazivom u sportskom centru vec postoji
     * ili ako prosledjeni sportski centar ne postoji u bazi
     */
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

    /**
     * Vraca listu svih sala iz baze mapiranih u DTO objekte
     *
     * @return lista SalaResponse DTO objekata sa podacima o svim salama
     */
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
