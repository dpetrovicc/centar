package rs.dp.sa.centar.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dp.sa.centar.dto.ClanskaKartaRequest;
import rs.dp.sa.centar.dto.ClanskaKartaResponse;
import rs.dp.sa.centar.entity.ClanskaKarta;
import rs.dp.sa.centar.entity.Korisnik;
import rs.dp.sa.centar.repository.ClanskaKartaRepository;
import rs.dp.sa.centar.repository.KorisnikRepository;

import java.time.LocalDate;

/**
 * Servis klasa za upravljanje clanskim kartama
 * Sadrzi logiku za kreiranje clanske karte
 *
 * @author Dusan Petrovic
 */
@Slf4j
@AllArgsConstructor
@Service
public class ClanskaKartaService {
    /**
     * Repozitorijum za rad sa podacima o clanskim kartama u bazi
     */
    private final ClanskaKartaRepository ckRepository;
    /**
     * Repozitorijum za rad sa podacima o korisnicima u bazi
     */
    private  final KorisnikRepository kRepository;

    /**
     * Kreiranje nove clanske karte, kao i njena aktivacija
     * Proverava se da li korisnik postoji u bazi,
     * a zatim se proverava je l' vec postoji clanska karta za tog korisnika
     * Datum aktivacije se postavlja na trenutni datum
     *
     * @param request DTO objekat koji sadrzi ID korisnika za kog se kreira clanska karta
     * @return ClanskaKartaResponse DTO objekat sa podacima o kreiranoj clanskoj karti i generisanim ID-em u bazi
     * @throws java.lang.RuntimeException ukoliko korisnik sa prosledjenim ID-em ne postoji, ili vec ima clansku kartu
     */
    @Transactional
    public ClanskaKartaResponse create(ClanskaKartaRequest request){
        Korisnik k = kRepository.findById(request.korisnikId())
                .orElseThrow(() -> new RuntimeException("Korisnik ne postoji"));

        if(ckRepository.existsByKorisnikKorisnikId(k.getKorisnikId())){
            throw new RuntimeException("Korisnik vec ima clansku kartu");
        }

        ClanskaKarta ck = new ClanskaKarta();
        ck.setKorisnik(k);
        ck.setDatumAktivacije(LocalDate.now());
        ck.setVaziDo(LocalDate.now().plusMonths(1));

        ClanskaKarta novaKarta = ckRepository.save(ck);

        return new ClanskaKartaResponse(
                novaKarta.getKartaId(),
                novaKarta.getDatumAktivacije(),
                novaKarta.getVaziDo(),
                novaKarta.getKorisnik().getKorisnikId()
        );
    }


}
