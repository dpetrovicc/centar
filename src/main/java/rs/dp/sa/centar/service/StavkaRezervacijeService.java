package rs.dp.sa.centar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dp.sa.centar.dto.StavkaRezervacijeRequest;
import rs.dp.sa.centar.dto.StavkaRezervacijeResponse;
import rs.dp.sa.centar.entity.RezervacijaTermina;
import rs.dp.sa.centar.entity.Sala;
import rs.dp.sa.centar.entity.StavkaRezervacije;
import rs.dp.sa.centar.repository.RezervacijaTerminaRepository;
import rs.dp.sa.centar.repository.SalaRepository;
import rs.dp.sa.centar.repository.StavkaRezervacijeRepository;

@Slf4j
@Service
@RequiredArgsConstructor

public class StavkaRezervacijeService {

    private final StavkaRezervacijeRepository srRepository;
    private final RezervacijaTerminaRepository rtRepository;
    private final SalaRepository sRepository;

    @Transactional
    public StavkaRezervacijeResponse create(StavkaRezervacijeRequest request){
        RezervacijaTermina rez = rtRepository.findById(request.rezervacijaId()).
                orElseThrow(() -> new RuntimeException("Nema prosledjenog ID-a Rezervacije u bazi"));

        Sala s = sRepository.findById(request.salaId())
                .orElseThrow(() -> new RuntimeException("Nema prosledjenog ID-a Sale bazi"));

        Double izracunataUkupnaCena = request.brojSati() * s.getCenaPoSatu();

        StavkaRezervacije sr = new StavkaRezervacije();
        sr.setRezervacijaTermina(rez);
        sr.setSala(s);
        sr.setCena(izracunataUkupnaCena);
        sr.setNapomena(request.napomena());
        sr.setBrojSati(request.brojSati());

        StavkaRezervacije novaSr = srRepository.save(sr);

        Double trenutnaUkupnaCena = rez.getUkupnaCena() != null ?  rez.getUkupnaCena() : 0.0;
        rez.setUkupnaCena(trenutnaUkupnaCena + izracunataUkupnaCena);
        rtRepository.save(rez);

        return new StavkaRezervacijeResponse(
                novaSr.getStavkaId(),
                novaSr.getRezervacijaTermina().getRezervacijaId(),
                novaSr.getSala().getSalaId(),
                novaSr.getCena(),
                novaSr.getNapomena(),
                novaSr.getBrojSati()
        );
    }


}
