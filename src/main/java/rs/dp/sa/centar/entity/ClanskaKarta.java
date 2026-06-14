package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clanska_karta")
@Getter
@Setter
@ToString
public class ClanskaKarta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "karta_id")
    private Long kartaId;

    @Column(name = "datum_aktivacije", nullable = false)
    private LocalDate datumAktivacije;

    @Column(name = "vazi_do", nullable = false)
    private LocalDate vaziDo;

    @OneToOne
    @JoinColumn(name = "korisnik_id", unique = true, nullable = false)
    private Korisnik korisnik;


}
