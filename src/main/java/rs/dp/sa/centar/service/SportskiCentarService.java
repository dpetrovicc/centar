package rs.dp.sa.centar.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.dp.sa.centar.dto.SportskiCentarRequest;
import rs.dp.sa.centar.dto.SportskiCentarResponse;
import rs.dp.sa.centar.entity.SportskiCentar;
import rs.dp.sa.centar.repository.SportskiCentarRepository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class SportskiCentarService {

    private final SportskiCentarRepository scRepository;

    public SportskiCentarResponse create(SportskiCentarRequest request){
        log.info("Kreiranje novog sportskog centra");

        SportskiCentar sc = new SportskiCentar();
        sc.setNaziv(request.naziv());
        sc.setAdresa(request.adresa());
        sc.setTelefon(request.telefon());

        SportskiCentar noviSc = scRepository.save(sc);

        return new SportskiCentarResponse(
                noviSc.getSportskiCentarId(),
                noviSc.getNaziv(),
                noviSc.getAdresa(),
                noviSc.getTelefon()
        );

    }

    public List<SportskiCentarResponse> getAll(){
        log.info("Dohvatanje svih sportskih centara iz baze");

        return scRepository.findAll().stream().map(
                sc -> new SportskiCentarResponse(
                        sc.getSportskiCentarId(),
                        sc.getNaziv(),
                        sc.getAdresa(),
                        sc.getTelefon()
                )
        ).toList();

    }

}
