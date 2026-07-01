package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull(message = "Datum izdavanja racuna ne sme biti null")
    @Column(name = "datum_izdavanja", nullable = false)
    private Date datumIzdavanja;

    /**
     * Ukupan iznos rezervacije izrazen u dinarima
     */
    @NotNull(message = "Ukupan iznos racuna ne sme biti null")
    @PositiveOrZero(message = "Ukupan iznos mora biti veci ili jednak nuli")
    @Column(name = "ukupan_iznos", nullable = false)
    private Double ukupanIznos;

    /**
     * Status da li je racun placen ili nije
     */
    @NotBlank(message = "Status placanja ne sme biti prazan")
    @Column(name = "status_placanja", nullable = false)
    private String statusPlacanja;

    /**
     * Rezervacija na koju se racun odnosi
     */
    @NotNull(message = "Rezervacija termina mora biti dodeljena racunu")
    @OneToOne
    @JoinColumn(name = "rezervacija_id", nullable = false, unique = true)
    private RezervacijaTermina rezervacijaTermina;


}
