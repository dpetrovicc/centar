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

@Service
@Slf4j
@RequiredArgsConstructor
public class TrenerService {

    private final TrenerRepository tRepository;
    private final SalaRepository sRepository;

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

    @Transactional
    public void delete(Long id){
        Trener t = tRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prosledjeni trener sa ID-jem ne postoji"));

        tRepository.delete(t);
    }

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
