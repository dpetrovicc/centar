package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Predstavlja korisnika sportskog centra.
 * Sadrzi informacije o imenu, prezimenu, email-u korisnika, kao i broj telefona
 * @author Dusan Petrovic
 */
@Entity
@Table(name = "korisnik")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Korisnik {

    /**
     * Jedinstveni identifikator korisnika u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnik_id")
    private Long korisnikId;

    /**
     * Ime korisnika sportskog centra
     */
    @Column(name = "ime", nullable = false)
    private String ime;

    /**
     * Prezime korisnika sportskog centra
     */
    @Column(name = "prezime", nullable = false)
    private String prezime;

    /**
     * Email korisnika
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Broj telefona korisnika
     */
    @Column(name = "telefon")
    private String telefon;

}
