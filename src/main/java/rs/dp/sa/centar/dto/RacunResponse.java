package rs.dp.sa.centar.dto;

import java.time.LocalDate;

public record RacunResponse(
        Long racunId,
        LocalDate datumIzdavanja,
        Double ukupanIznos,
        String statusPlacanja,
        Long rezervacijaId
) {
}
