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
import static org.mockito.Mockito.mock;

class SalaTest {

    private Validator validator;
    private SportskiCentar mockSportskiCentar;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        mockSportskiCentar = mock(SportskiCentar.class);
    }

    @Test
    void getNaziv() {

        Sala s = new Sala(null, "Hala 1", "DA", 2500.0, mockSportskiCentar);
        assertEquals("Hala 1", s.getNaziv());

    }

    @Test
    void setCenaPoSatu() {
        Sala s = new Sala();
        s.setCenaPoSatu(2500.0);
        assertEquals(2500.0, s.getCenaPoSatu());

    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravnu salu")
    void validate_ValidSala_NoViolations(){
        Sala s = new Sala(null, "Hala 1", "DA", 2500.0, mockSportskiCentar);

        Set<ConstraintViolation<Sala>> violations = validator.validate(s);

        assertTrue(violations.isEmpty());

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je naziv prazan")
    void validate_BlankNaziv_FailsValidation() {

        Sala s = new Sala(null, "", "DA", 2500.0,  mockSportskiCentar);

        Set<ConstraintViolation<Sala>> violations = validator.validate(s);

        assertFalse(violations.isEmpty(), "Neuspesno, prazan naziv je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Naziv sale ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je cena po satu nula ili negativna")
    void validate_InvalidCenaPoSatu_FailsValidation() {
        Sala s = new Sala(null, "Hala 1", "DA", 0.0,  mockSportskiCentar);

        Set<ConstraintViolation<Sala>> violations = validator.validate(s);

        assertFalse(violations.isEmpty(), "Neuspesno, cena 0 je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Cena po satu mora biti najmanje 1", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je sportski centar null")
    void validate_NullSportskiCentar_FailsValidation() {
        Sala s = new Sala(null, "Hala 1", "DA", 2500.0,  null);

        Set<ConstraintViolation<Sala>> violations = validator.validate(s);

        assertFalse(violations.isEmpty(), "Neuspesno, prosao je null sportski centar");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Sportski centar mora biti dodeljen sali", msg);

    }

}