package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Predstavlja trenera koji radi u sali sportskog centra
 * Sadrzi informacije o imenu trenera, prezimenu, kontakt telefonu i sali u kojoj radi
 * @author Dusan Petrovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trener")
@Getter
@Setter
@ToString
public class Trener {

    /**
     * Jedinstveni identifikator trenera u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trener_id")
    private Long trenerId;

    /**
     * Ime trenera
     */
    @Column(name = "ime", nullable = false)
    private String ime;

    /**
     * Prezime trenera
     */
    @Column(name = "prezime", nullable = false)
    private String prezime;

    /**
     * Kontakt telefon trenera
     */
    @Column(name = "telefon", nullable = false)
    private String telefon;

    /**
     * Sala u kojoj trener radi i vodi treninge
     */
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

}
