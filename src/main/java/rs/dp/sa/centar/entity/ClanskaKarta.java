package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 *Clanska karta korisnika sportskog centra.
 * Sadrzi informacije o datumu aktivacije, periodu vazenja i kom korisniku pripada
 * @author Dusan Petrovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clanska_karta")
@Getter
@Setter
@ToString
public class ClanskaKarta {

    /**
     * Jedinstveni identifikator clanske karte u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "karta_id")
    private Long kartaId;

    /**
     * Datum kada je clanska karta aktivirana
     */
    @Column(name = "datum_aktivacije", nullable = false)
    private LocalDate datumAktivacije;

    /**
     * Datum do kog clanska karta vazi
     */
    @Column(name = "vazi_do", nullable = false)
    private LocalDate vaziDo;

    /**
     * Korisnik koji je vlasnik ove clanske karte
     */
    @OneToOne
    @JoinColumn(name = "korisnik_id", unique = true, nullable = false)
    private Korisnik korisnik;


}
