package rs.dp.sa.centar.dto;

import jakarta.validation.constraints.NotBlank;

public record SportskiCentarRequest(
        @NotBlank(message = "Naziv sportskog centra ne sme biti prazan")
        String naziv,
        @NotBlank(message = "Adresa sportskog centra ne sme biti prazna")
        String adresa,
        @NotBlank(message = "Telefon sportskog centra ne sme biti prazan")
        String telefon
) {
}
