package rs.dp.sa.centar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dp.sa.centar.dto.RacunRequest;
import rs.dp.sa.centar.dto.RacunResponse;
import rs.dp.sa.centar.entity.Racun;
import rs.dp.sa.centar.entity.RezervacijaTermina;
import rs.dp.sa.centar.repository.RacunRepository;
import rs.dp.sa.centar.repository.RezervacijaTerminaRepository;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class RacunService {

    private final RacunRepository rRepository;
    private final RezervacijaTerminaRepository rtRepository;

    @Transactional
    public RacunResponse create(RacunRequest request){
        RezervacijaTermina rt = rtRepository.findById(request.rezervacijaId())
                .orElseThrow(() -> new RuntimeException("Rezervacija sa ovim ID-em ne postoji"));

        Racun rac = new Racun();
        rac.setRezervacijaTermina(rt);
        rac.setDatumIzdavanja(new Date());
        rac.setStatusPlacanja("Nije placeno");
        rac.setUkupanIznos(rt.getUkupnaCena());

        Racun r = rRepository.save(rac);
        return new RacunResponse(
                r.getRacunId(),
                r.getDatumIzdavanja(),
                r.getUkupanIznos(),
                r.getStatusPlacanja(),
                r.getRezervacijaTermina().getRezervacijaId()
        );

    }

}
