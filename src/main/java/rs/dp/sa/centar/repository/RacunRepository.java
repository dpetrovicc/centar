package rs.dp.sa.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dp.sa.centar.entity.Racun;

@Repository
public interface RacunRepository extends JpaRepository<Racun, Long> {
}
