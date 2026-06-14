package rs.dp.sa.centar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.dp.sa.centar.dto.KorisnikRequest;
import rs.dp.sa.centar.dto.KorisnikResponse;
import rs.dp.sa.centar.entity.Korisnik;
import rs.dp.sa.centar.repository.KorisnikRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    @Transactional
    public KorisnikResponse create(KorisnikRequest request) {
        log.info("Kreiranje novog korisnika");

        if (korisnikRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Ovaj email vec postoji");
        }

        Korisnik k = new Korisnik();
        k.setIme(request.ime());
        k.setPrezime(request.prezime());
        k.setEmail(request.email());
        k.setTelefon(request.telefon());

        return mapToResponse(korisnikRepository.save(k));
    }

    private KorisnikResponse mapToResponse(Korisnik k){
        return new KorisnikResponse(
                k.getKorisnikId(),
                k.getIme(),
                k.getPrezime(),
                k.getEmail(),
                k.getTelefon()
        );
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<KorisnikResponse> findAll(){
        return korisnikRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

}
