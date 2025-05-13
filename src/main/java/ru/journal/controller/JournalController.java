package ru.journal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.journal.service.CBRService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/journal")
public class JournalController {
    private final CBRService cbrService;

    @PostMapping("/update")
    public ResponseEntity<String> updateRates(){
        if (cbrService.handleRates()) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error parsing XML response");
    }
}
