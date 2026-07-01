package rs.dp.sa.centar.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SportskiCentarTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getNaziv() {
        SportskiCentar sc = new SportskiCentar(null, "Vizura", "Cara Dusana 105", "011222333");
        assertEquals("Vizura", sc.getNaziv());

    }

    @Test
    void setAdresa() {
        SportskiCentar sc = new SportskiCentar();
        sc.setAdresa("Cara Dusana 105");
        assertEquals("Cara Dusana 105", sc.getAdresa());
    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravan sportski centar")
    void validate_ValidSportskiCentar_NoViolations() {
        SportskiCentar sc = new SportskiCentar(null, "Vizura", "Cara Dusana 105", "011222333");

        Set<ConstraintViolation<SportskiCentar>> violations = validator.validate(sc);

        assertTrue(violations.isEmpty());

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je naziv prazan")
    void validate_BlankNaziv_FailsValidation() {
        SportskiCentar sc = new SportskiCentar(null, "", "Cara Dusana 105", "011222333");

        Set<ConstraintViolation<SportskiCentar>> violations = validator.validate(sc);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan naziv je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Naziv sportskog centra ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je adresa prazna")
    void validate_BlankAdresa_FailsValidation(){
        SportskiCentar sc = new SportskiCentar(null, "Vizura", "  ", "011222333");

        Set<ConstraintViolation<SportskiCentar>> violations = validator.validate(sc);

        assertFalse(violations.isEmpty(), "Neuspesno, prazna adresa je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Adresa sportskog centra ne sme biti prazna", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je telefon prazan")
    void validate_BlankTelefon_FailsValidation() {
        SportskiCentar sc = new SportskiCentar(null, "Vizura", "Cara Dusana 105", "");

        Set<ConstraintViolation<SportskiCentar>> violations = validator.validate(sc);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan telefon je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Telefon sportskog centra ne sme biti prazan", msg);


    }


}