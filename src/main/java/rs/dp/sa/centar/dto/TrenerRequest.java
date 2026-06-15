package rs.dp.sa.centar.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TrenerRequest(
        @NotBlank(message = "Ime trenera je obavezno")
        String ime,
        @NotBlank(message = "Prezime trenera je obavezno")
        String prezime,
        @NotBlank(message = "Telefon je obavezan")
        String telefon,
        @NotNull(message = "ID sale je obavezan")
        Long salaId
) {
}
