package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.dp.sa.centar.dto.RacunRequest;
import rs.dp.sa.centar.dto.RacunResponse;
import rs.dp.sa.centar.entity.Racun;
import rs.dp.sa.centar.repository.RacunRepository;
import rs.dp.sa.centar.service.RacunService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/racun")
public class RacunController {

    private final RacunService rService;
    private final RacunRepository rRepository;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody RacunRequest request){
        try {
            RacunResponse rac = rService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(rac);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(){
        try {
            List<Racun> racuni = rRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(racuni);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
