package rs.dp.sa.centar.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RezervacijaTerminaTest {

    private Validator  validator;
    private Korisnik mockKorisnik;
    private SportskiCentar  mockSportskiCentar;

    @BeforeEach
    void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        mockKorisnik = Mockito.mock(Korisnik.class);
        mockSportskiCentar = Mockito.mock(SportskiCentar.class);

    }

    @Test
    void getUkupnaCena() {

        RezervacijaTermina rt = new RezervacijaTermina(null, LocalDate.now(), 4500.0, "DA", mockKorisnik, mockSportskiCentar);
        assertEquals(4500.0, rt.getUkupnaCena());

    }

    @Test
    void setOdobreno() {
        RezervacijaTermina rt = new RezervacijaTermina();
        rt.setOdobreno("DA");
        assertEquals("DA", rt.getOdobreno());
    }

    @Test
    @DisplayName("Validacija treba da prodje za ispravnu rezervaciju")
    void validate_ValidRezervacija_NoViolations() {

        RezervacijaTermina rt = new RezervacijaTermina(null, LocalDate.now(), 4500.0, "DA", mockKorisnik, mockSportskiCentar);

        Set<ConstraintViolation<RezervacijaTermina>> violations = validator.validate(rt);

        assertTrue(violations.isEmpty());


    }

    @Test
    @DisplayName("Validacija treba da failuje kada je datum null")
    void validate_NullDatum_FailsValidation(){
        RezervacijaTermina rt = new RezervacijaTermina(null, null, 4500.0, "DA", mockKorisnik, mockSportskiCentar);

        Set<ConstraintViolation<RezervacijaTermina>> violations = validator.validate(rt);

        assertFalse(violations.isEmpty(), "Neuspesno, null datum je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Datum rezervacije ne sme biti null", msg);


    }

    @Test
    @DisplayName("Validacija treba da failuje kada je cena u minusu")
    void validate_NegativeCena_FailsValidation() {
        RezervacijaTermina rt = new RezervacijaTermina(null, LocalDate.now(), -4500.0, "DA", mockKorisnik, mockSportskiCentar);

        Set<ConstraintViolation<RezervacijaTermina>> violations = validator.validate(rt);
        assertFalse(violations.isEmpty(), "Neuspesno, negativna cena je prosla");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Ukupna cena mora biti veca ili jednaka nuli", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je status odobrenja prazan")
    void validate_BlankOdobreno_FailsValidation() {
        RezervacijaTermina rt = new RezervacijaTermina(null, LocalDate.now(), 4500.0, "   ", mockKorisnik, mockSportskiCentar);

        Set<ConstraintViolation<RezervacijaTermina>> violations = validator.validate(rt);
        assertFalse(violations.isEmpty(), "Neuspesno, prazan status odobrenja je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Status odobrenja ne sme biti prazan", msg);
    }

    @Test
    @DisplayName("Validacija treba da failuje kada je korisnik null")
    void validate_NullKorisnik_FailsValidation(){
        RezervacijaTermina rt = new RezervacijaTermina(null, LocalDate.now(), 4500.0, "DA", null, mockSportskiCentar);

        Set<ConstraintViolation<RezervacijaTermina>> violations = validator.validate(rt);
        assertFalse(violations.isEmpty(), "Neuspesno, null korisnik je prosao");
        assertEquals(1, violations.size());

        String msg =  violations.iterator().next().getMessage();
        assertEquals("Korisnik mora biti dodeljen rezervaciji", msg);

    }

    @Test
    @DisplayName("Validacija treba da failuje kada je sportski centar null")
    void validate_NullSportskiCentar_FailsValidation() {
        RezervacijaTermina rt = new RezervacijaTermina(null, LocalDate.now(), 4500.0, "DA", mockKorisnik, null);

        Set<ConstraintViolation<RezervacijaTermina>> violations = validator.validate(rt);
        assertFalse(violations.isEmpty(), "Neuspesno, null sportski centar je prosao");
        assertEquals(1, violations.size());

        String msg = violations.iterator().next().getMessage();
        assertEquals("Sportski centar mora biti dodeljen rezervaciji", msg);
    }


}