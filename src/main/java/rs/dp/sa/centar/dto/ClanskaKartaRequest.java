package rs.dp.sa.centar.dto;

import jakarta.validation.constraints.NotNull;

public record ClanskaKartaRequest(
        @NotNull(message = "ID korisnika je obavezan")
        Long korisnikId
) {
}
