package rs.dp.sa.centar.entity;

import jakarta.persistence.*;
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
    @Column(name = "broj_sati", nullable = false)
    private Integer brojSati;

    /**
     * Rezervacija kojoj stavka pripada
     */
    @ManyToOne
    @JoinColumn(name = "rezervacija_id", nullable = false)
    private RezervacijaTermina rezervacijaTermina;

    /**
     * Sala koja ce biti zakupljena
     */
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;



}
