package com.example.vac.service;

import com.example.vac.repo.VacRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j

public class VacRedisService {
    private final VacRepo vacRepo;
//    private final RedisService redisService;
    private final RedisTemplate<String, String> redisTemplate;
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
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            var stringCommands = connection.stringCommands();

            for (String record : records) {
                stringCommands.set(
                        (record).getBytes(),
                        ("value: 646545646564654655465632132123132123").getBytes()
                );
            }
            return null;
        });

    }

    public String findByVac(String vac) {
        for (int i = 4; i <= 10 && i <= vac.length(); i++) {
            String subVac = redisTemplate.opsForValue().get(vac.substring(0, i));
            if (subVac != null) {
                return subVac;
            }
        }
        return null;
    }

    public Set<String> findKeysByPrefix(String prefix) {
        Set<String> keys = new HashSet<>();

        ScanOptions options = ScanOptions.scanOptions()
                .match(prefix + "*")
                .count(1000)
                .build();

        try (Cursor<byte[]> cursor =
                     redisTemplate.getConnectionFactory()
                             .getConnection()
                             .scan(options)) {

            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
        }

        return keys;
    }

}
