package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Ime trenera ne sme biti prazno")
    @Column(name = "ime", nullable = false)
    private String ime;

    /**
     * Prezime trenera
     */
    @NotBlank(message = "Prezime trenera ne sme biti prazno")
    @Column(name = "prezime", nullable = false)
    private String prezime;

    /**
     * Kontakt telefon trenera
     */
    @NotBlank(message = "Telefon trenera ne sme biti prazan")
    @Column(name = "telefon", nullable = false)
    private String telefon;

    /**
     * Sala u kojoj trener radi i vodi treninge
     */
    @NotNull(message = "Sala mora biti dodeljena treneru")
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

}
