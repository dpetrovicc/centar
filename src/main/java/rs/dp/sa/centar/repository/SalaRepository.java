package rs.dp.sa.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dp.sa.centar.entity.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNazivAndSportskiCentarSportskiCentarId(String naziv, Long sportskiCentarId);
}
