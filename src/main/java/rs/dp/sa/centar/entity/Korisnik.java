package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "korisnik")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnik_id")
    private Long korisnikId;

    @Column(name = "ime", nullable = false)
    private String ime;

    @Column(name = "prezime", nullable = false)
    private String prezime;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefon")
    private String telefon;

}
