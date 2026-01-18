package com.example.vac.service;

import com.example.vac.repo.VacRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacService {
    private final VacRepo vacRepo;

    private static final PatriciaTrie<String> trie = new PatriciaTrie<>();

    private static final Map<Integer, PatriciaTrie<String>> trieMap = new HashMap<>();

    private static final int PAGE_SIZE = 10000;

    public void processAllRecords() {
        long numberOfRecords = vacRepo.countRecords();
        long numberOfPages = (int) Math.ceil((double) numberOfRecords / PAGE_SIZE);
        int pageNumber = 0;

        while (pageNumber <= numberOfPages) {
            List<String> records = fetchPage(pageNumber);
            log.info("PAGE: " + pageNumber);
            if (records.isEmpty()) {
                break;
            }

            process(records);

            pageNumber++;
        }
    }

    public void processAllRecordsWithArr() {
        long numberOfRecords = vacRepo.countRecords();
        long numberOfPages = (int) Math.ceil((double) numberOfRecords / PAGE_SIZE);
        int pageNumber = 0;

        while (pageNumber <= numberOfPages) {
            List<String> records = fetchPage(pageNumber);

            if (records.isEmpty()) {
                break;
            }

            processArr(records);

            pageNumber++;
        }
        trieMap.forEach((key, value) -> {
            log.info("Trie contain vac lengh: {} ======== Trie size: {}", key, value.size());
        });
    }

    private List<String> fetchPage(int pageNumber) {
        return vacRepo.findByPaging(VacService.PAGE_SIZE, pageNumber);
    }

    private void processArr(List<String> records) {
        for (String record : records) {
            if (trieMap.get(record.length()) == null) {
                PatriciaTrie<String> trieEl = new PatriciaTrie<>();
                trieEl.put(record, "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
                trieMap.put(record.length(), trieEl);
            } else {
                PatriciaTrie<String> trieEl = trieMap.get(record.length());
                trieEl.put(record, "12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop12345678910LKJHGFasdhqweyrtuiop");
            }
        }
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

    public Map<String, String> findByVac(String vac) {
        Map<String, String> res = new HashMap<>();
        for (int i = 4; i <= 10 && i <= vac.length(); i++) {
            String subVac = vac.substring(0, i);
            String trieFind = trie.get(subVac);
            if (trieFind != null) {
                res.put(subVac, trieFind);
                return res;
            }
        }

        return res;

//        SortedMap<String, String> res = null;
//        int minLength = 3;
//        while (minLength < vac.length()) {
//            res = trie.prefixMap(vac.substring(0, minLength + 1));
//            if (!res.isEmpty()) {
//                break;
//            }
//            else {
//                minLength++;
//            }
//        }
//        return res;
    }

    public Map<String, String> checkForInsert(String vac) {
        SortedMap<String, String> res = null;
        int minLength = 3;
        int vacLength = vac.length();
        while (vacLength > minLength) {
            res = trie.prefixMap(vac.substring(0, vacLength));
            if (!res.isEmpty()) {
                break;
            } else {
                vacLength--;
            }
        }
        return res;
    }

    public Map<String, String> findByVacArr(String vac) {

        int minLength = 3;
        Map<String, String> finalRes = new HashMap<>();
        while (minLength < vac.length()) {
            PatriciaTrie<String> trieEl = trieMap.get(minLength + 1);
            String res = trieEl.get(vac.substring(0, minLength + 1));
            if (res != null) {
                finalRes.put(vac.substring(0, minLength + 1), res);
            }
            minLength++;
        }
        return finalRes;
    }

}
