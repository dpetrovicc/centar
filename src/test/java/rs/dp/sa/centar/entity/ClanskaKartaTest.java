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

class ClanskaKartaTest {

    private Validator validator;
    private Korisnik mockKorisnik;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        mockKorisnik = mock(Korisnik.class);
    }

    @Test
    void getDatumAktivacije() {

        LocalDate danas = LocalDate.now();
        ClanskaKarta ck = new ClanskaKarta(null, danas, danas.plusMonths(1), mockKorisnik);

        assertEquals(danas, ck.getDatumAktivacije());

    }

    @Test
    void setVaziDo() {
        LocalDate buducnost = LocalDate.now().plusMonths(1);
        ClanskaKarta ck = new ClanskaKarta();
        ck.setVaziDo(buducnost);

        assertEquals(buducnost, ck.getVaziDo());
    }

    @Test
    @DisplayName("Validacija za ispravnu clansku kartu")
    void validate_ValidClanskaKarta_NoViolations() {
        LocalDate danas = LocalDate.now();
        ClanskaKarta ck = new ClanskaKarta(null, danas,  danas.plusMonths(1), mockKorisnik);

        Set<ConstraintViolation<ClanskaKarta>> violations = validator.validate(ck);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je datum aktivacije null")
    void validate_NullDatumAktivacije_FailsValidation() {
        ClanskaKarta ck = new ClanskaKarta(null, null,  LocalDate.now().plusMonths(1), mockKorisnik);

        Set<ConstraintViolation<ClanskaKarta>> violations = validator.validate(ck);

        assertFalse(violations.isEmpty(), "Neuspesno, prosao je null datum aktivacije");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Datum aktivacije ne sme biti null", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je korisnik null")
    void validate_NullKorisnik_FailsValidation() {
        ClanskaKarta ck = new ClanskaKarta(null, LocalDate.now(),  LocalDate.now().plusMonths(1), null);

        Set<ConstraintViolation<ClanskaKarta>> violations = validator.validate(ck);

        assertFalse(violations.isEmpty(), "Neuspesno prosao je null korisnik");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Korisnik mora biti dodeljen clanskoj karti", msg);

    }
}