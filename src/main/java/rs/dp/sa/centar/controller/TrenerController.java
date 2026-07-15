package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.dp.sa.centar.dto.TrenerRequest;
import rs.dp.sa.centar.dto.TrenerResponse;
import rs.dp.sa.centar.service.TrenerService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/trener")
public class TrenerController {

    private final TrenerService tService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody TrenerRequest request){
        try {
            TrenerResponse response = tService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody TrenerRequest request){
        try {
            TrenerResponse response = tService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        try {
            tService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Trener uspesno obrisan");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        try {
            TrenerResponse response = tService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
