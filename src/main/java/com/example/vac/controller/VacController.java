package com.example.vac.controller;

import com.example.vac.service.BulkInsertService;
import com.example.vac.service.VacService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vac")
@RequiredArgsConstructor
@Slf4j
public class VacController {
    private final BulkInsertService bulkInsertService;
    private final VacService vacService;

    @PostMapping("/insert")
    public ResponseEntity<?> createDumData() {
        bulkInsertService.insertExactlyFiveMillion();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<?> load() {
        vacService.processAllRecords();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
