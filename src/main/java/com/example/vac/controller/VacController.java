package com.example.vac.controller;

import com.example.vac.service.BulkInsertService;
import com.example.vac.service.VacRedisService;
import com.example.vac.service.VacService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.SortedMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vac")
@RequiredArgsConstructor
@Slf4j
public class VacController {
    private final BulkInsertService bulkInsertService;
    private final VacService vacService;
    private final VacRedisService vacRedisService;

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

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String vac) {
//        vacService.initDataTest();
        SortedMap<String, String> res = vacService.findByVac(vac);
        if (Objects.isNull(res)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/redis/load")
    public ResponseEntity<?> loadRedisVac() {
        vacRedisService.processAllRecords();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
