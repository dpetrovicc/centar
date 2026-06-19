package rs.dp.sa.centar.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StavkaRezervacijeRequest(
        @NotNull(message = "Id rezervacije je obavezan")
        Long rezervacijaId,

        @NotNull(message = "Id sale je obavezan")
        Long salaId,
        @NotNull(message = "Broj sati je obavezan")
        @Min(value = 1, message = "Broj sati mora biti najmanje 1")
        Integer brojSati,
        String napomena
) {
}
