package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Naziv sale ne sme biti prazan")
    @Column(name = "naziv")
    private String naziv;

    /**
     * Da li je sala na otvorenom ili ne
     */
    @NotBlank(message = "Polje na otvorenom ne sme biti prazno")
    @Column(name = "na_otvorenom")
    private String naOtvorenom;

    /**
     * Cena koriscenja sale po satu izrazena u dinarima
     */
    @NotNull(message = "Cena po satu mora biti uneta")
    @Min(value = 1, message = "Cena po satu mora biti najmanje 1")
    @Column(name = "cena_po_satu")
    private Double cenaPoSatu;

    /**
     * Sportski centar u kom se sala nalazi
     */
    @NotNull(message = "Sportski centar mora biti dodeljen sali")
    @ManyToOne
    @JoinColumn(name = "sportski_centar_id", nullable = false)
    private SportskiCentar sportskiCentar;

}
