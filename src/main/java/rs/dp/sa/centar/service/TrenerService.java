package rs.dp.sa.centar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dp.sa.centar.dto.TrenerRequest;
import rs.dp.sa.centar.dto.TrenerResponse;
import rs.dp.sa.centar.entity.Sala;
import rs.dp.sa.centar.entity.Trener;
import rs.dp.sa.centar.repository.SalaRepository;
import rs.dp.sa.centar.repository.TrenerRepository;

/**
 * Servis klasa za upravljanje podacima o trenerima u sportskom centru
 * Sadrzi logiku za kreiranje. izmenu, brisanje i pronalazenje trenera po njegovom ID-u
 *
 * @author Dusan Petrovic
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TrenerService {

    /**
     * Repozitorijum za rad sa podacima o trenerima u bazi podataka
     */
    private final TrenerRepository tRepository;
    /**
     * Repozitorijum za rad sa podacima o salama u bazi podataka
     */
    private final SalaRepository sRepository;

    /**
     * Kreira novog trenera i dodeljuje mu salu u kojoj radi
     * Proverava se da li postoji prosledjena sala
     *
     * @param request DTO objekat koji sadrzi podatke ime, prezime, telefon i ID sale za novog trenera
     * @return TrenerResponse DTO objekat sa podacima o kreiranom treneru i
     * generisanim ID-em u bazi
     * @throws java.lang.RuntimeException ukoliko ne postoji sala sa prosledjenim ID-em
     */
    @Transactional
    public TrenerResponse create(TrenerRequest request){
        Sala s = sRepository.findById(request.salaId()).
                orElseThrow(() -> new RuntimeException("Sala sa prosledjenim ID-jem ne postoji u bazi"));

        Trener t = new Trener();
        t.setIme(request.ime());
        t.setPrezime(request.prezime());
        t.setTelefon(request.telefon());
        t.setSala(s);

        Trener tr = tRepository.save(t);

        return new TrenerResponse(
                tr.getTrenerId(),
                tr.getIme(),
                tr.getPrezime(),
                tr.getTelefon(),
                tr.getSala().getSalaId()
        );

    }

    /**
     * Menja podatke o postojecem treneru u bazi preko njegovog ID-a
     * Proverava se da li postoji trener sa prosledjenim ID-em u bazi,
     * kao i postojanje sale sa prosledjenim ID-em
     *
     * @param id jedinstveni identifikator trenera koji se menja
     * @param request DTO objekat sa novim podacima o treneru i ID-em sale
     * @return TrenerResponse DTO objekat sa azuriranim podacima iz baze
     * @throws java.lang.RuntimeException ukoliko trener sa porsledjenim ID-em ne postoji u bazi,
     * ili ukoliko sala sa prosledjenim ID-em ne postoji u bazi
     */
    @Transactional
    public TrenerResponse update(Long id, TrenerRequest request){
        Trener t = tRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trener sa prosledjenim ID-jem ne postoji u bazi"));

        Sala s = sRepository.findById(request.salaId()).
                orElseThrow(() ->  new RuntimeException("Promenjena sala sa ID-jem ne postoji"));

        t.setIme(request.ime());
        t.setPrezime(request.prezime());
        t.setTelefon(request.telefon());
        t.setSala(s);

        Trener izmenjen = tRepository.save(t);

        return new TrenerResponse(
                izmenjen.getTrenerId(),
                izmenjen.getIme(),
                izmenjen.getPrezime(),
                izmenjen.getTelefon(),
                izmenjen.getSala().getSalaId()
        );

    }

    /**
     * Brise trenera iz baze na osnovu prosledjenog ID-a trenera
     * Proverava se da li postoji trener sa prosledjenim ID-em
     *
     * @param id jedinstveni identifikator trenera koji se brise
     * @throws java.lang.RuntimeException ukoliko trener sa prosledjenim ID-em ne postoji
     */
    @Transactional
    public void delete(Long id){
        Trener t = tRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prosledjeni trener sa ID-jem ne postoji"));

        tRepository.delete(t);
    }

    /**
     * Pronalazi trenera u bazi na osnovu prosledjenog ID-a
     *
     * @param id jedinstveni identifikator trazenog trenera
     * @return TrenerResponse DTO objekat sa podacima o pronadjenom treneru iz baze
     * @throws java.lang.RuntimeException ukoliko trener sa prosledjenim ID-em ne postoji u bazi
     */
    @Transactional(readOnly = true)
    public TrenerResponse findById(Long id){
        Trener t = tRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nema trenera sa tim ID-jem u bazi"));
        return new TrenerResponse(
                t.getTrenerId(),
                t.getIme(),
                t.getPrezime(),
                t.getTelefon(),
                t.getSala().getSalaId()
        );
    }
}
