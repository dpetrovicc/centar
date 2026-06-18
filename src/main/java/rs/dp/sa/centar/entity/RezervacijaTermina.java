package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rezervacija_termina")
@ToString
public class RezervacijaTermina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervacija_id")
    private Long rezervacijaId;

    @Column(name = "datum", nullable = false)
    private Date datum;

    @Column(name = "ukupna_cena")
    private Double ukupnaCena = 0.0;

    @Column(name = "odobreno", nullable = false)
    private String odobreno = "NE";

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "sportski_centar_id", nullable = false)
    private SportskiCentar sportskiCentar;


}
