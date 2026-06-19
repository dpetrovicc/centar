package rs.dp.sa.centar.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record RezervacijaTerminaRequest(
        @NotNull(message = "ID sportskog centra je obavezan")
        Long sportskiCentarId,
        @NotNull(message = "ID korisnika je obavezan")
        Long korisnikId,
        @NotNull(message = "Datum je obavezan")
        Date datum
) {
}
