package rs.dp.sa.centar.dto;

public record KorisnikResponse(
        Long korisnikId,
        String ime,
        String prezime,
        String email,
        String telefon
) {
}
