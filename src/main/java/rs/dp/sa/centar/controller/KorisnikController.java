package rs.dp.sa.centar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.dp.sa.centar.dto.KorisnikRequest;
import rs.dp.sa.centar.dto.KorisnikResponse;
import rs.dp.sa.centar.service.KorisnikService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/korisnik")
public class KorisnikController {

    private final KorisnikService korisnikService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid KorisnikRequest request){
        try {
            KorisnikResponse kp = korisnikService.create(request);
            return ResponseEntity.status(HttpStatus.OK).body(kp);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(){
        try {
            List<KorisnikResponse> lista = korisnikService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
