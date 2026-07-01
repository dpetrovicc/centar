package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull(message = "Datum rezervacije ne sme biti null")
    @Column(name = "datum", nullable = false)
    private Date datum;

    /**
     * Suma svih stavki rezervacija izrazena u dinarima
     */
    @NotNull(message = "Ukupna cena ne sme biti null")
    @PositiveOrZero(message = "Ukupna cena mora biti veca ili jednaka nuli")
    @Column(name = "ukupna_cena")
    private Double ukupnaCena = 0.0;

    /**
     * Status rezervacije DA/NE
     */
    @NotBlank(message = "Status odobrenja ne sme biti prazan")
    @Column(name = "odobreno", nullable = false)
    private String odobreno = "NE";

    /**
     * Korisnik na kog glasi rezervacija
     */
    @NotNull(message = "Korisnik mora biti dodeljen rezervaciji")
    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    /**
     * Sportski centar na koji se odnosi rezervacija
     */
    @NotNull(message = "Sportski centar mora biti dodeljen rezervaciji")
    @ManyToOne
    @JoinColumn(name = "sportski_centar_id", nullable = false)
    private SportskiCentar sportskiCentar;


}
