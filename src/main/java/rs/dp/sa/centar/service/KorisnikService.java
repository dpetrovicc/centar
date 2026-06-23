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

/**
 * Servis klasa koja sadezi logiku za kreiranje novog korisnika
 * kao i vracanje svih korisnika iz baze preko njegovog repozitorijuma
 * @author Dusan Petrovic
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KorisnikService {

    /**
     * Repozitorijum za direktnu komunikaciju sa bazom u okviru tabele korisnik
     */
    private final KorisnikRepository korisnikRepository;

    /**
     * Kreira novog korisnika sa prosledjenim podacima
     * Vrsi se provera da li vec postoji prosledjeni email
     * @param request DTO objekat koji sadrzi podatke za kreiranje novog korisnika(ime, prezime, email, telefon)
     * @return KorisnikResponse DTO objekat sa sacuvanim podacima i generisanim ID-em
     * @throws java.lang.RuntimeException ako email vec postoji u bazi, baca se izuzetak
     */
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

    /**
     * Pomocna metoda koja mapira entiteski objekat Korisnik u DTO KorisnikResponse objekat
     * @param k entitet Korisnik koji se mapira
     * @return KorisnikResponse DTO objekat
     */
    private KorisnikResponse mapToResponse(Korisnik k){
        return new KorisnikResponse(
                k.getKorisnikId(),
                k.getIme(),
                k.getPrezime(),
                k.getEmail(),
                k.getTelefon()
        );
    }

    /**
     * Vraca listu svih korisnika iz baze mapiranih u DTO objekte KorisnikResponse
     * @return
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<KorisnikResponse> findAll(){
        return korisnikRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

}
