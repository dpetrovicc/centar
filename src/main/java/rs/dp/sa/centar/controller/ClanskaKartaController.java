package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.dp.sa.centar.dto.ClanskaKartaRequest;
import rs.dp.sa.centar.dto.ClanskaKartaResponse;
import rs.dp.sa.centar.service.ClanskaKartaService;

@RestController
@RequestMapping("/api/clanska-karta")
@AllArgsConstructor
public class ClanskaKartaController {

    private final ClanskaKartaService ckService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ClanskaKartaRequest request) {

        try {
            ClanskaKartaResponse ckr = ckService.create(request);
            return ResponseEntity.status(HttpStatus.OK).body(ckr);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
