package rs.dp.sa.centar.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KorisnikTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getIme() {
        Korisnik korisnik = new Korisnik(null, "Nikola", "Nikolic", "nikola@gmail.com", "065111222");
        assertEquals("Nikola", korisnik.getIme());
    }

    @Test
    void getEmail() {
        Korisnik korisnik = new Korisnik(null, "Nikola", "Nikolic", "nikola@gmail.com", "065111222");
        assertEquals("nikola@gmail.com", korisnik.getEmail());
    }

    @Test
    void setIme() {
        Korisnik k = new Korisnik();
        k.setIme("Nikola");

        assertEquals("Nikola", k.getIme());
    }

    @Test
    void setEmail() {
        Korisnik k = new Korisnik();
        k.setEmail("nikola@gmail.com");
        assertEquals("nikola@gmail.com", k.getEmail());
    }


    @ParameterizedTest
    @CsvSource({
            "Marko, Markovic, marko@gmail.com, 065123456",
            "Nikola, Nikolic, nikola@yahoo.com, +38164111222",
            "Joko, Jokic, joko@fon.bg.ac.rs, ''"
    })
    @DisplayName("Validacija treba da prodje za ispravne objekte Korisnika")
    void validate_ValidKorisnik_NoViolations(String ime, String prezime, String email, String telefon){
        Korisnik k = new Korisnik(null, ime, prezime, email, telefon);

        Set<ConstraintViolation<Korisnik>> violations = validator.validate(k);

        assertTrue(violations.isEmpty());

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je prezime prazno")
    void validate_BlankPrezime_FailsValidation(){
        Korisnik korisnik = new Korisnik(null, "Nikola", "", "nikola@gmail.com", "065111222");

        Set<ConstraintViolation<Korisnik>> violations = validator.validate(korisnik);

        assertFalse(violations.isEmpty(), "Neuspesno, prazno prezime je proslo");
        assertEquals(1, violations.size());

        String msg =  violations.iterator().next().getMessage();
        assertEquals("Prezime korisnika ne sme biti prazno", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je email prazan")
    void validate_BlankEmail_FailsValidation() {
        Korisnik korisnik = new Korisnik(null, "Nikola", "Nikolic", " ", "065111222");

        Set<ConstraintViolation<Korisnik>> violations = validator.validate(korisnik);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan email je prosao");

        String svePoruke = violations.toString();
        assertTrue(svePoruke.contains("Email ne sme biti prazan"));

    }


    @Test
    @DisplayName("Validacija treba da failuje kada je email u neispravnom formatu")
    void validate_InvalidEmailFormat_FailsValidation() {
        Korisnik korisnik = new Korisnik(null, "Nikola", "Nikolic", "losemail", "065111222");

        Set<ConstraintViolation<Korisnik>> violations = validator.validate(korisnik);

        assertFalse(violations.isEmpty(), "Neispravan email je prosao");
        String msg = violations.iterator().next().getMessage();
        assertEquals("Email mora biti u ispravnom formatu", msg);
    }


}