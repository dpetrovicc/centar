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

/**
 * Servis klasa za upravljanje rezervacijama termina u sportskom centru
 * Sadrzi logiku za kreiranje, pronalazenje i otkaz rezervacija
 *
 * @author Dusan Petrovic
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RezervacijaTerminaService {

    /**
     * Repozitorijum za rad sa podacima o korisnicima
     */
    private final KorisnikRepository kRepository;
    /**
     * Repozitorijum za rad sa podacima o sportskim centrima
     */
    private final SportskiCentarRepository scRepository;
    /**
     * Repozitorijum za rad sa podacima o rezervacijama
     */
    private final RezervacijaTerminaRepository rtRepository;

    /**
     * Kreira novu rezervaciju termina za odredjenog korisnika u odredjenom sportskom centru
     * Proverava se da li korisnik sa tim ID-em postoji u bazi, kao i sportski centar
     * Inicijalna cena se postavlja na 0.0, a status odobrenja na NE
     *
     * @param request DTO objekat koji sadrzi datum, ID korisnika i ID sportskog centra
     * @return RezervacijaTerminaResponse DTO objekat sa podacima o kreiranoj rezervaciji
     * @throws java.lang.RuntimeException ukoliko korisnik sa prosledjenim ID-em ne postoji u bazi,
     * ili ukoliko sportski centar sa prosledjenim ID-em ne postoji u bazi
     */
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

    /**
     * Pronalazi rezervaciju u bazi na osnovu prosledjenog ID-a
     *
     * @param id jedinstveni identifikator trazene rezervacije
     * @return RezervacijaTerminaResponse DTO objekat sa podacim trazene rezervacije
     * @throws java.lang.RuntimeException ukoliko rezervacija sa prosledjenim ID-em ne postoji u bazi
     */
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

    /**
     * Otkazuje postojecu rezervaciju u bazi promenom njenog statusa odobrenja u OTKAZANO
     * Proverava se da li trazena rezervacija postoji u bazi
     *
     * @param id jedinstveni identifikator rezervacije koju je potrebno otkazati
     * @return RezervacijaTerminaResponse DTO objekat sa ažuriranim statusom OTKAZANO
     * @throws java.lang.RuntimeException ukoliko rezervacija sa prosledjenim ID-em ne postoji u bazi
     */
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
