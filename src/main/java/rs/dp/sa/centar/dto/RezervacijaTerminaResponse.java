package rs.dp.sa.centar.dto;

import java.time.LocalDate;

public record RezervacijaTerminaResponse(
        Long rezervacijaId,
        LocalDate datum,
        Double ukupnaCena,
        String odobreno,
        Long korisnikId,
        Long sportskiCentarId
) {
}
