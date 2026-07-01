package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Predstavlja stavku rezervacije koja sadrzi informacije o ceni, opcionoj napomeni korisnika,
 * broju sati koliko ce biti zakupljena sala i rezervaciju termina
 * na koji se odnosi stavka, kao i sali na koju se odnosi
 * @author Dusan Petrovic
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stavka_rezervacije")
@Getter
@Setter
@ToString
public class StavkaRezervacije {

    /**
     * Jedinstveni identifikator stavke rezervacije u bazi
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stavka_id")
    private Long stavkaId;

    /**
     * Cena iznajmljivanja termina izrazena u dinarima
     */
    @NotNull(message = "Cena stavke ne sme biti null")
    @Positive(message = "Cena stavke mora biti veca od nule")
    @Column(name = "cena", nullable = false)
    private Double cena;

    /**
     * Opciona napomena korisnika prilikom rezervisanja termina u nekoj sali
     */
    @Column(name = "napomena")
    private String napomena;

    /**
     * Broj sati na koliko ce odredjena sala biti zakupljena
     */
    @NotNull(message = "Broj sati ne sme biti null")
    @Positive(message = "Broj sati mora biti veci od nule")
    @Column(name = "broj_sati", nullable = false)
    private Integer brojSati;

    /**
     * Rezervacija kojoj stavka pripada
     */
    @NotNull(message = "Rezervacija termina mora biti dodeljena stavci")
    @ManyToOne
    @JoinColumn(name = "rezervacija_id", nullable = false)
    private RezervacijaTermina rezervacijaTermina;

    /**
     * Sala koja ce biti zakupljena
     */
    @NotNull(message = "Sala mora biti dodeljena stavci")
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;



}
