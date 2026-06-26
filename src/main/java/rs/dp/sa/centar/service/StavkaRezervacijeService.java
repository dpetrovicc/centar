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

/**
 * Servis klasa za upravljanje stavkama rezervacije
 * Sadrzi logiku za kreiranje, tj dodavanje novih stavki u okviru postojece rezervacije,
 * pri cemu automatski azurira ukupnu vrednost rezervacije
 *
 * @author Dusan Petrovic
 */
@Slf4j
@Service
@RequiredArgsConstructor

public class StavkaRezervacijeService {

    /**
     * Repozitorijum za rad sa podacima o stavkama rezervacije
     */
    private final StavkaRezervacijeRepository srRepository;
    /**
     * Repozitorijum za rad sa podacima o rezervacijama
     */
    private final RezervacijaTerminaRepository rtRepository;
    /**
     * Repozitorijum za rad sa podacima o salama
     */
    private final SalaRepository sRepository;

    /**
     * Kreiranje nove stavke rezervacije u okviru postojece rezervacije
     * Proverava da li prosledjena rezervacija postoji u bazi, kao i sala koja treba da se doda,
     * racuna se cena nove stavke, i azurira ukupna cena rezervacije
     *
     * @param request DTO objekat koji sadrzi ID rezervacije, ID sale, broj sati i napomenu
     * @return StavkaRezervacijeResponse DTO objekat sa kreiranom stavkom i generisanim ID-em
     * @throws java.lang.RuntimeException ukoliko ne postoji prosledjena rezervacija, ili prosledjena sala u bazi
     */
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
