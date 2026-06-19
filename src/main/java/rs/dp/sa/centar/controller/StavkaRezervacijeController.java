package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.dp.sa.centar.dto.StavkaRezervacijeRequest;
import rs.dp.sa.centar.dto.StavkaRezervacijeResponse;
import rs.dp.sa.centar.service.StavkaRezervacijeService;

@RestController
@Slf4j
@RequestMapping("/api/stavka-rezervacije")
@RequiredArgsConstructor
public class StavkaRezervacijeController {

    private final StavkaRezervacijeService srService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody StavkaRezervacijeRequest request){
        try {
            StavkaRezervacijeResponse srr = srService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(srr);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
