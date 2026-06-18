package rs.dp.sa.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dp.sa.centar.entity.RezervacijaTermina;

@Repository
public interface RezervacijaTerminaRepository extends JpaRepository<RezervacijaTermina, Long> {
}
