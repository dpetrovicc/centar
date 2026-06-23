package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Predstavlja salu koja se nalazi u odredjenom sportskom centru
 * Sadrzi informacije o nazivu, ceni po satu koriscenja i da li je na otvorenom
 * @author Dusan Petrovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sala")
@Getter
@Setter
@ToString
public class Sala {
    /**
     * Jedinstveni identifikator sale u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaId;

    /**
     * Naziv sale u sportskom centru
     */
    @Column(name = "naziv")
    private String naziv;

    /**
     * Da li je sala na otvorenom ili ne
     */
    @Column(name = "na_otvorenom")
    private String naOtvorenom;

    /**
     * Cena koriscenja sale po satu izrazena u dinarima
     */
    @Column(name = "cena_po_satu")
    private Double cenaPoSatu;

    /**
     * Sportski centar u kom se sala nalazi
     */
    @ManyToOne
    @JoinColumn(name = "sportski_centar_id", nullable = false)
    private SportskiCentar sportskiCentar;

}
