package rs.dp.sa.centar.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SalaRequest(
        @NotBlank(message = "Naziv sale je obavezan")
        String naziv,

        @NotBlank(message = "Polje naOtvorenom mora biti popunjeno")
        String naOtvorenom,

        @NotNull(message = "Polje cenaPoSatu je obavezno")
        @Positive(message = "Cena mora biti veca od nule")
        Double cenaPoSatu,

        @NotNull(message = "ID sportskog centra je obavezan")
        Long sportskiCentarId

) {
}
