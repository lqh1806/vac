package com.example.vac.service;

import com.example.vac.entity.Vac;
import com.example.vac.repo.VacRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacService {
    private final VacRepo vacRepo;

    private final PatriciaTrie<Vac> trie = new PatriciaTrie<>();

    private static final int PAGE_SIZE = 10000;

    public void processAllRecords() {
        int pageNumber = 0;

        while (true) {
            List<Vac> records = fetchPage(pageNumber);

            if (records.isEmpty()) {
                break;
            }

            process(records);

            pageNumber++;
        }
    }

    private List<Vac> fetchPage(int pageNumber) {
        return vacRepo.findByPaging(VacService.PAGE_SIZE, pageNumber);
    }

    private void process(List<Vac> records) {
        for (Vac record : records) {
            trie.put(record.getVac(), null);
        }
        log.info("TRIE size: {}", trie.size());
    }

}
