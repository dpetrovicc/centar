package rs.dp.sa.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dp.sa.centar.entity.ClanskaKarta;

@Repository
public interface ClanskaKartaRepository extends JpaRepository<ClanskaKarta, Long> {
    boolean existsByKorisnikKorisnikId(Long korisnikId);
}
