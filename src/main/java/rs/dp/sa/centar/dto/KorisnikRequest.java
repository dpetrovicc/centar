package rs.dp.sa.centar.dto;

import jakarta.validation.constraints.NotBlank;

public record KorisnikRequest(

        @NotBlank(message = "Ime je obavezno") String ime,
        @NotBlank(message = "Prezime je obavezno") String prezime,
        @NotBlank(message = "Email je obavezan") String email,
        String telefon
) {
}
