package rs.dp.sa.centar.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrenerTest {

    private Validator validator;
    private Sala mockSala;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        mockSala = Mockito.mock(Sala.class);
    }

    @Test
    void getIme() {
        Trener t = new Trener(null, "Dusan", "Petrovic", "065111222", mockSala);
        assertEquals("Dusan", t.getIme());

    }

    @Test
    void setPrezime() {
        Trener t = new Trener();
        t.setPrezime("Petrovic");
        assertEquals("Petrovic", t.getPrezime());
    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravnog trenera")
    void validate_ValidTrener_NoViolations() {
        Trener t = new Trener(null, "Dusan", "Petrovic", "065111222", mockSala);

        Set<ConstraintViolation<Trener>> violations = validator.validate(t);
        assertTrue(violations.isEmpty());

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je ime prazno")
    void validate_BlankIme_FailsValidation() {
        Trener t = new Trener(null, "", "Petrovic", "065111222", mockSala);

        Set<ConstraintViolation<Trener>> violations = validator.validate(t);
        assertFalse(violations.isEmpty(), "Neuspesno, prazno ime je proslo");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Ime trenera ne sme biti prazno", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je prezime prazno")
    void validate_BlankPrezime_FailsValidation(){
        Trener t = new Trener(null, "Dusan", "", "065111222", mockSala);

        Set<ConstraintViolation<Trener>> violations = validator.validate(t);
        assertFalse(violations.isEmpty(), "Neuspesno, prazno prezime je proslo");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Prezime trenera ne sme biti prazno", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je telefon prazan")
    void validate_BlankTelefon_FailsValidation(){
        Trener t = new Trener(null, "Dusan", "Petrovic", "", mockSala);

        Set<ConstraintViolation<Trener>> violations = validator.validate(t);
        assertFalse(violations.isEmpty(), "Neuspesno, prazan telefon je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Telefon trenera ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je sala null")
    void validate_NullSala_FailsValidation(){
        Trener t = new Trener(null, "Dusan", "Petrovic", "065111222", null);

        Set<ConstraintViolation<Trener>> violations = validator.validate(t);

        assertFalse(violations.isEmpty(), "Neuspesno, null sala je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Sala mora biti dodeljena treneru", msg);



    }

}