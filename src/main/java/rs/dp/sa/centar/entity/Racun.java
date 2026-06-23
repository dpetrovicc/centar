package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Predstavlja fiskalni racun koji se kreira za rezervaciju sa svim terminima, i izracunatim ukupnim iznosom
 * Poseduje takodje datum izdavanja racuna, kao i status da li je placen racun
 * @author Dusan Petrovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "racun")
@Getter
@Setter
@ToString
public class Racun {

    /**
     * Jedinstveni identifikator racuna u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "racun_id")
    private Long racunId;

    /**
     * Datum kada je racun izdat
     */
    @Column(name = "datum_izdavanja", nullable = false)
    private Date datumIzdavanja;

    /**
     * Ukupan iznos rezervacije izrazen u dinarima
     */
    @Column(name = "ukupan_iznos", nullable = false)
    private Double ukupanIznos;

    /**
     * Status da li je racun placen ili nije
     */
    @Column(name = "status_placanja", nullable = false)
    private String statusPlacanja;

    /**
     * Rezervacija na koju se racun odnosi
     */
    @OneToOne
    @JoinColumn(name = "rezervacija_id", nullable = false, unique = true)
    private RezervacijaTermina rezervacijaTermina;


}
