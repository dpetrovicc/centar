package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "racun")
@Getter
@Setter
@ToString
public class Racun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "racun_id")
    private Long racunId;

    @Column(name = "datum_izdavanja", nullable = false)
    private Date datumIzdavanja;

    @Column(name = "ukupan_iznos", nullable = false)
    private Double ukupanIznos;

    @Column(name = "status_placanja", nullable = false)
    private String statusPlacanja;

    @OneToOne
    @JoinColumn(name = "rezervacija_id", nullable = false, unique = true)
    private RezervacijaTermina rezervacijaTermina;


}
