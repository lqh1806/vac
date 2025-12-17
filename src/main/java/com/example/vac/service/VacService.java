package com.example.vac.service;

import com.example.vac.repo.VacRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SortedMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacService {
    private final VacRepo vacRepo;

    private final PatriciaTrie<String> trie = new PatriciaTrie<>();

    private static final int PAGE_SIZE = 10000;

    public void processAllRecords() {
        long numberOfRecords = vacRepo.countRecords();
        long numberOfPages = numberOfRecords / PAGE_SIZE + 1;
        int pageNumber = 0;

        while (pageNumber < numberOfPages) {
            List<String> records = fetchPage(pageNumber);

            if (records.isEmpty()) {
                break;
            }

            process(records);

            pageNumber++;
        }
    }

    private List<String> fetchPage(int pageNumber) {
        return vacRepo.findByPaging(VacService.PAGE_SIZE, pageNumber);
    }

    private void process(List<String> records) {
        for (String record : records) {
            trie.put(record, "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
        }
        log.info("TRIE size: {}", trie.size());
    }

    public void initDataTest() {
        trie.put("123J", "1");
        trie.put("123JV", "12");
        trie.put("123JXC", "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
        trie.put("123J234", "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
        trie.put("123JCXZF", "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
        trie.put("123AB", "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
    }

    public SortedMap<String, String> findByVac(String vac) {
        SortedMap<String, String> res = null;
        int minLength = 3;
        while (minLength < vac.length()) {
            res = trie.prefixMap(vac.substring(0, minLength + 1));
            if (!res.isEmpty()) {
                break;
            }
            else {
                minLength++;
            }
        }
        return res;
    }

}
