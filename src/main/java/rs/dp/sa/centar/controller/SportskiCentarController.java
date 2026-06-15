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
import rs.dp.sa.centar.entity.SportskiCentar;
import rs.dp.sa.centar.repository.SportskiCentarRepository;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sportski-centar")
public class SportskiCentarController {

    private final SportskiCentarRepository scRepository;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody SportskiCentar sc) {
        try {
            SportskiCentar novi = scRepository.save(sc);
            return ResponseEntity.status(HttpStatus.CREATED).body(novi);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
