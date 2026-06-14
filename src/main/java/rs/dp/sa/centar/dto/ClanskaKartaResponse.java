package rs.dp.sa.centar.dto;

import java.time.LocalDate;

public record ClanskaKartaResponse(
        Long kartaId,
        LocalDate datumAktivacije,
        LocalDate vaziDo,
        Long korisnikId

) {
}
