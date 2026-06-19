package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.dp.sa.centar.dto.RezervacijaTerminaRequest;
import rs.dp.sa.centar.dto.RezervacijaTerminaResponse;
import rs.dp.sa.centar.service.RezervacijaTerminaService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/rezervacija-termina")
public class RezervacijaTerminaController {

    private final RezervacijaTerminaService rtService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody RezervacijaTerminaRequest request){
        try {
            RezervacijaTerminaResponse response = rtService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        try {
            RezervacijaTerminaResponse rt = rtService.findById(id);
            return  ResponseEntity.status(HttpStatus.OK).body(rt);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
