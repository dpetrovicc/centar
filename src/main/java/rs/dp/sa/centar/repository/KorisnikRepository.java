package rs.dp.sa.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dp.sa.centar.entity.Korisnik;

import java.util.Optional;
@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik,Long> {
    Optional<Korisnik> findByEmail(String email);
}
