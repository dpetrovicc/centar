package rs.dp.sa.centar.dto;

import java.util.Date;

public record RezervacijaTerminaResponse(
        Long rezervacijaId,
        Date datum,
        Double ukupnaCena,
        String odobreno,
        Long korisnikId,
        Long sportskiCentarId
) {
}
