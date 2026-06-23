package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Predstavlja rezervaciju vezanu za odredjeni sportski centar na odredjeni datum i ukupnom cenom rezervisanja termina
 * @author Dusan Petrovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rezervacija_termina")
@ToString
public class RezervacijaTermina {

    /**
     *Jedinstveni identifikator rezervacije u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervacija_id")
    private Long rezervacijaId;

    /**
     * Datum odrzavanja rezervisanog termina
     */
    @Column(name = "datum", nullable = false)
    private Date datum;

    /**
     * Suma svih stavki rezervacija izrazena u dinarima
     */
    @Column(name = "ukupna_cena")
    private Double ukupnaCena = 0.0;

    /**
     * Status rezervacije DA/NE
     */
    @Column(name = "odobreno", nullable = false)
    private String odobreno = "NE";

    /**
     * Korisnik na kog glasi rezervacija
     */
    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    /**
     * Sportski centar na koji se odnosi rezervacija
     */
    @ManyToOne
    @JoinColumn(name = "sportski_centar_id", nullable = false)
    private SportskiCentar sportskiCentar;


}
