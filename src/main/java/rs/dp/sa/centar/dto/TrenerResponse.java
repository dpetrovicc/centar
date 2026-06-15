package rs.dp.sa.centar.dto;

public record TrenerResponse(
        Long trenerId,
        String ime,
        String prezime,
        String telefon,
        Long salaId
) {
}
