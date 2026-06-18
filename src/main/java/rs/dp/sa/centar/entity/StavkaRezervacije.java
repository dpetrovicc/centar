package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stavka_rezervacije")
@Getter
@Setter
@ToString
public class StavkaRezervacije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stavka_id")
    private Long stavkaId;

    @Column(name = "cena", nullable = false)
    private Double cena;

    @Column(name = "napomena")
    private String napomena;

    @Column(name = "broj_sati", nullable = false)
    private Integer brojSati;

    @ManyToOne
    @JoinColumn(name = "rezervacija_id", nullable = false)
    private RezervacijaTermina rezervacijaTermina;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;



}
