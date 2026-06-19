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
import rs.dp.sa.centar.dto.RacunRequest;
import rs.dp.sa.centar.dto.RacunResponse;
import rs.dp.sa.centar.entity.Racun;
import rs.dp.sa.centar.service.RacunService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/racun")
public class RacunController {

    private final RacunService rService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody RacunRequest request){
        try {
            RacunResponse rac = rService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(rac);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
