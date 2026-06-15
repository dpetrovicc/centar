package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.dp.sa.centar.dto.SalaRequest;
import rs.dp.sa.centar.dto.SalaResponse;
import rs.dp.sa.centar.service.SalaService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sala")
public class SalaController {

    private final SalaService sService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody SalaRequest request){
        try {
            SalaResponse response = sService.create(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(){
        try {
            List<SalaResponse> response = sService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
