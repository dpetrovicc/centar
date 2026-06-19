package rs.dp.sa.centar.dto;

public record StavkaRezervacijeResponse(
        Long stavkaId,
        Long rezervacijaId,
        Long salaId,
        Double cena,
        String napomena,
        Integer brojSati
) {
}
