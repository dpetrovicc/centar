package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sala")
@Getter
@Setter
@ToString
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaId;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "na_otvorenom")
    private String naOtvorenom;

    @Column(name = "cena_po_satu")
    private Double cenaPoSatu;

    @ManyToOne
    @JoinColumn(name = "sportski_centar_id", nullable = false)
    private SportskiCentar sportskiCentar;

}
