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

/**
 * Servis klasa za upravljanje racunima u sportskom centru
 * Sadrzi logiku za kreiranje novog racuna na osnovu rezervacije i svih njenih stavki
 *
 * @author Dusan Petrovic
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RacunService {

    /**
     * Repozitorijum za rad sa podacima o racunima
     */
    private final RacunRepository rRepository;
    /**
     * Repozitorijum za rad sa podacima o rezervacijama
     */
    private final RezervacijaTerminaRepository rtRepository;

    /**
     * Kreiranje novog racuna na osnovu prosledjene rezervacije
     * Proverava da li prosledjena rezervacija postoji u bazi na osnovu ID-a
     * Ukupnu cenu racuna uzima i postavlja na ukupan iznos racuna
     * Postavlja datum izdavanja na trenutni datum i inijcalizuje status placanja na "Nije placeno"
     *
     * @param request DTO objekat koji sadrzi jedinstveni identifikator rezervacije
     * da bi znao na koju rezeraciju se odnosi racun
     * @return RacunResponse DTO objekat sa podacima o kreiranom racunu i generisanim ID-em
     * @throws java.lang.RuntimeException ukoliko prosledjena rezervacija ne postoji u bazi na osnovu ID-a
     */
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
