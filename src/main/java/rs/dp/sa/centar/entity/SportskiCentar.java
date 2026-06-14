package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sportski_centar")
@Getter
@Setter
@ToString
public class SportskiCentar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sportski_centar_id")
    private Long sportskiCentarId;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon")
    private String telefon;


}
