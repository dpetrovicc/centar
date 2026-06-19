package rs.dp.sa.centar.dto;

import jakarta.validation.constraints.NotNull;

public record RacunRequest(
        @NotNull(message = "ID rezervacije je obavezan parametar za kreiranje racuna")
        Long rezervacijaId
) {
}
