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
import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class ClanskaKartaService {
    private final ClanskaKartaRepository ckRepository;
    private  final KorisnikRepository kRepository;

    @Transactional
    public ClanskaKartaResponse create(ClanskaKartaRequest request){
        Korisnik k = kRepository.findById(request.korisnikId())
                .orElseThrow(() -> new RuntimeException("Korisnik ne postoji"));

        if(ckRepository.existByKorisnikKorisnikId(k.getKorisnikId())){
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
