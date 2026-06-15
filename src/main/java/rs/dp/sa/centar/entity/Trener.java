package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trener")
@Getter
@Setter
@ToString
public class Trener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trener_id")
    private Long trenerId;

    @Column(name = "ime", nullable = false)
    private String ime;

    @Column(name = "prezime", nullable = false)
    private String prezime;

    @Column(name = "telefon", nullable = false)
    private String telefon;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

}
