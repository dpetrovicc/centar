package rs.dp.sa.centar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dp.sa.centar.dto.RezervacijaTerminaRequest;
import rs.dp.sa.centar.dto.RezervacijaTerminaResponse;
import rs.dp.sa.centar.entity.Korisnik;
import rs.dp.sa.centar.entity.RezervacijaTermina;
import rs.dp.sa.centar.entity.SportskiCentar;
import rs.dp.sa.centar.repository.KorisnikRepository;
import rs.dp.sa.centar.repository.RezervacijaTerminaRepository;
import rs.dp.sa.centar.repository.SportskiCentarRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class RezervacijaTerminaService {

    private final KorisnikRepository kRepository;
    private final SportskiCentarRepository scRepository;
    private final RezervacijaTerminaRepository rtRepository;

    @Transactional
    public RezervacijaTerminaResponse create(RezervacijaTerminaRequest request){
        Korisnik k = kRepository.findById(request.korisnikId())
                .orElseThrow(() -> new RuntimeException("Nema korisnika sa tim ID-em u bazi"));

        SportskiCentar sc = scRepository.findById(request.sportskiCentarId())
                .orElseThrow(() -> new RuntimeException("Nema sportskog centra sa tim ID-em u bazi"));

        RezervacijaTermina rez = new RezervacijaTermina();
        rez.setDatum(request.datum());
        rez.setKorisnik(k);
        rez.setSportskiCentar(sc);
        rez.setOdobreno("NE");
        rez.setUkupnaCena(0.0);

        RezervacijaTermina novaRez = rtRepository.save(rez);

        return new RezervacijaTerminaResponse(
                novaRez.getRezervacijaId(),
                novaRez.getDatum(),
                novaRez.getUkupnaCena(),
                novaRez.getOdobreno(),
                novaRez.getKorisnik().getKorisnikId(),
                novaRez.getSportskiCentar().getSportskiCentarId()
        );

    }

    @Transactional(readOnly = true)
    public RezervacijaTerminaResponse findById(Long id){

        RezervacijaTermina rt = rtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nema rezervacije sa unetim ID-em"));

        return new RezervacijaTerminaResponse(
                rt.getRezervacijaId(),
                rt.getDatum(),
                rt.getUkupnaCena(),
                rt.getOdobreno(),
                rt.getKorisnik().getKorisnikId(),
                rt.getSportskiCentar().getSportskiCentarId()
        );


    }

    @Transactional
    public RezervacijaTerminaResponse cancel(Long id){
        RezervacijaTermina rez = rtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ne posotoji rezervacija sa ovim ID-em u bazi"));

        rez.setOdobreno("OTKAZANO");

        RezervacijaTermina nova = rtRepository.save(rez);

        return new RezervacijaTerminaResponse(
                nova.getRezervacijaId(),
                nova.getDatum(),
                nova.getUkupnaCena(),
                nova.getOdobreno(),
                nova.getKorisnik().getKorisnikId(),
                nova.getSportskiCentar().getSportskiCentarId()
        );

    }
}
