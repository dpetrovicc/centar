package rs.dp.sa.centar.dto;

import java.util.Date;

public record RacunResponse(
        Long racunId,
        Date datumIzdavanja,
        Double ukupanIznos,
        String statusPlacanja,
        Long rezervacijaId
) {
}
