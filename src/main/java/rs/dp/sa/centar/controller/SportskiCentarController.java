package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.dp.sa.centar.dto.SportskiCentarRequest;
import rs.dp.sa.centar.dto.SportskiCentarResponse;
import rs.dp.sa.centar.service.SportskiCentarService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sportski-centar")
public class SportskiCentarController {

    private final SportskiCentarService sService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody SportskiCentarRequest sc) {
        try {
            SportskiCentarResponse novi = sService.create(sc);
            return ResponseEntity.status(HttpStatus.CREATED).body(novi);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(){
        try {
            List<SportskiCentarResponse> scs = sService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(scs);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
