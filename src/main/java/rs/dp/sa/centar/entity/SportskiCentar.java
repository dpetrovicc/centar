package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Predstavlja sportski centar koji sadrzi informacije o nazivu, adresi na kojoj se nalazi i broj telefona centra
 * @author Dusan Petrovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sportski_centar")
@Getter
@Setter
@ToString
public class SportskiCentar {

    /**
     * Jedinstveni identifikator sportskog centra u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sportski_centar_id")
    private Long sportskiCentarId;

    /**
     * Naziv odredjenog sportskog centra
     */
    @Column(name = "naziv")
    private String naziv;

    /**
     * Adresa na kojoj se nalazi sportski centar
     */
    @Column(name = "adresa")
    private String adresa;

    /**
     * Kontakt telefon centra
     */
    @Column(name = "telefon")
    private String telefon;


}
