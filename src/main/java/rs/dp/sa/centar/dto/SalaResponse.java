package rs.dp.sa.centar.dto;

public record SalaResponse(
        Long salaId,
        String naziv,
        String naOtvorenom,
        Double cenaPoSatu,
        Long sportskiCentarId
) {
}
