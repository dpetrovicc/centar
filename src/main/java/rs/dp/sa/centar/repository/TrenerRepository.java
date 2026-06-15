package rs.dp.sa.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dp.sa.centar.entity.Trener;

@Repository
public interface TrenerRepository extends JpaRepository<Trener, Long> {
}
