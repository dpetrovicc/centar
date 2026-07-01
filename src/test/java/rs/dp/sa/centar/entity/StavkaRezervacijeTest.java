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

class StavkaRezervacijeTest {

    private Validator validator;
    private RezervacijaTermina mockRezervacijaTermina;
    private Sala mockSala;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        mockRezervacijaTermina = Mockito.mock(RezervacijaTermina.class);
        mockSala = Mockito.mock(Sala.class);
    }

    @Test
    void getCena() {
        StavkaRezervacije sr = new StavkaRezervacije(null, 1500.0, "Opciono", 1, mockRezervacijaTermina, mockSala);
        assertEquals(1500.0, sr.getCena());
    }

    @Test
    void setNapomena() {
        StavkaRezervacije sr = new StavkaRezervacije();
        sr.setNapomena("Potrebna vaterpolo lopta");
        assertEquals("Potrebna vaterpolo lopta",  sr.getNapomena());
    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravnu stavku rezervacije")
    void validate_ValidStavka_NoViolations(){
        StavkaRezervacije sr = new StavkaRezervacije(null, 1500.0, "Opciono", 1, mockRezervacijaTermina, mockSala);

        Set<ConstraintViolation<StavkaRezervacije>> violations = validator.validate(sr);

        assertTrue(violations.isEmpty());

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je cena null")
    void validate_NullCena_FailsValidation(){
        StavkaRezervacije sr = new StavkaRezervacije(null, null, "Opciono", 1, mockRezervacijaTermina, mockSala);

        Set<ConstraintViolation<StavkaRezervacije>> violations = validator.validate(sr);

        assertFalse(violations.isEmpty(), "Neuspesno, null cena je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Cena stavke ne sme biti null", msg);


    }

    @Test
    @DisplayName("Validacija treba da failuje kada je cena nula ili negativna")
    void validate_InvalidCena_FailsValidation() {
        StavkaRezervacije sr = new StavkaRezervacije(null, 0.0, "Opciono", 1, mockRezervacijaTermina, mockSala);

        Set<ConstraintViolation<StavkaRezervacije>> violations = validator.validate(sr);
        assertFalse(violations.isEmpty(), "Neuspesno, cena nula je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Cena stavke mora biti veca od nule", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je broj sati null")
    void validate_NullBrojSati_FailsValidation() {
        StavkaRezervacije sr = new StavkaRezervacije(null, 1500.0, "Opciono", null, mockRezervacijaTermina, mockSala);

        Set<ConstraintViolation<StavkaRezervacije>> violations = validator.validate(sr);
        assertFalse(violations.isEmpty(), "Neuspesno, null broj sati je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Broj sati ne sme biti null", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je broj sati negativan")
    void validate_InvalidBrojSati_FailsValidation() {
        StavkaRezervacije sr = new StavkaRezervacije(null, 1500.0, "Opciono", -1, mockRezervacijaTermina, mockSala);

        Set<ConstraintViolation<StavkaRezervacije>> violations = validator.validate(sr);
        assertFalse(violations.isEmpty(), "Neuspesno, negativan broj sati je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Broj sati mora biti veci od nule", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je rezervacija termina null")
    void validate_NullRezervacijaTermina_FailsValidation() {
        StavkaRezervacije sr = new StavkaRezervacije(null, 1500.0, "Opciono", 1, null, mockSala);

        Set<ConstraintViolation<StavkaRezervacije>> violations = validator.validate(sr);
        assertFalse(violations.isEmpty(), "Neuspesno, null rezervacija je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Rezervacija termina mora biti dodeljena stavci", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je sala null")
    void validate_NullSala_FailsValidation() {
        StavkaRezervacije sr = new StavkaRezervacije(null, 1500.0, "Opciono", 1, mockRezervacijaTermina, null);

        Set<ConstraintViolation<StavkaRezervacije>> violations = validator.validate(sr);
        assertFalse(violations.isEmpty(), "Neuspesno, null sala je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Sala mora biti dodeljena stavci", msg);
    }




}