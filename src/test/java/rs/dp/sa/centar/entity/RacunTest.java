package rs.dp.sa.centar.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RacunTest {

    private Validator validator;
    private RezervacijaTermina mockRezervacijaTermina;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        mockRezervacijaTermina = mock(RezervacijaTermina.class);
    }

    @Test
    void getUkupanIznos() {

        Racun r = new Racun(null, LocalDate.now(), 5000.0, "PLACENO", mockRezervacijaTermina);
        assertEquals(5000.0, r.getUkupanIznos());
    }

    @Test
    void setStatusPlacanja() {
        Racun r = new Racun();
        r.setStatusPlacanja("PLACENO");
        assertEquals("PLACENO", r.getStatusPlacanja());
    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravan racun")
    void validate_ValidRacun_NoViolations(){
        Racun r = new Racun(null, LocalDate.now(), 5000.0, "PLACENO", mockRezervacijaTermina);

        Set<ConstraintViolation<Racun>> violations = validator.validate(r);

        assertTrue(violations.isEmpty());


    }

    @Test
    @DisplayName("Validacija treba da failuje kada je datum izdavanja null")
    void validate_NullDatumIzdavanja_FailsValidation(){
        Racun r = new Racun(null, null, 5000.0, "PLACENO", mockRezervacijaTermina);

        Set<ConstraintViolation<Racun>> violations = validator.validate(r);
        assertFalse(violations.isEmpty(), "Neuspesno, null datum izdavanja je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Datum izdavanja racuna ne sme biti null", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je ukupan iznos null")
    void validate_NullUkupanIznos_FailsValidation() {
        Racun r = new Racun(null, LocalDate.now(), null, "PLACENO", mockRezervacijaTermina);

        Set<ConstraintViolation<Racun>> violations = validator.validate(r);
        assertFalse(violations.isEmpty(), "Neuspesno, null iznos je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Ukupan iznos racuna ne sme biti null", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je iznos negativan")
    void validate_NegativeUkupanIznos_FailsValidation() {
        Racun r = new Racun(null, LocalDate.now(), -5000.0, "PLACENO", mockRezervacijaTermina);

        Set<ConstraintViolation<Racun>> violations = validator.validate(r);
        assertFalse(violations.isEmpty(), "Neuspesno, negativan iznos je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Ukupan iznos mora biti veci ili jednak nuli", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je status placanja prazan")
    void validate_BlankStatusPlacanja_FailsValidation() {
        Racun r = new Racun(null, LocalDate.now(), 5000.0, "  ", mockRezervacijaTermina);

        Set<ConstraintViolation<Racun>> violations = validator.validate(r);
        assertFalse(violations.isEmpty(), "Neuspesno, prazan status placanja je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Status placanja ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je RezervacijaTermina null")
    void validate_NullRezervacijaTermina_FailsValidation() {
        Racun r = new Racun(null, LocalDate.now(), 5000.0, "PLACENO", null);

        Set<ConstraintViolation<Racun>> violations = validator.validate(r);

        assertFalse(violations.isEmpty(), "Neuspesno, null rezervacija je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Rezervacija termina mora biti dodeljena racunu", msg);

    }



}