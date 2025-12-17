package com.example.vac.service;

import com.example.vac.repo.VacRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class VacRedisService {
    private final VacRepo vacRepo;
    private final RedisService redisService;

    private static final int PAGE_SIZE = 10000;

    public void processAllRecords() {
        long numberOfRecords = vacRepo.countRecords();
        long numberOfPages = numberOfRecords / PAGE_SIZE + 1;
        int pageNumber = 0;

        int numOfRecord = 0;
        while (pageNumber < numberOfPages) {
            List<String> records = fetchPage(pageNumber);

            if (records.isEmpty()) {
                break;
            }

            process(records);

            pageNumber++;
            numOfRecord += records.size();
            log.info("REDIS size: {}", numOfRecord);
        }
    }

    private List<String> fetchPage(int pageNumber) {
        return vacRepo.findByPaging(PAGE_SIZE, pageNumber);
    }

    private void process(List<String> records) {
        for (String record : records) {
            redisService.save(record, "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
        }
    }

}
