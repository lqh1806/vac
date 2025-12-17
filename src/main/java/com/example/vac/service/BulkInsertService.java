package com.example.vac.service;

import com.example.vac.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BulkInsertService {

    private static final int TARGET_COUNT = 5_000_000;
    private static final int BATCH_SIZE = 1000;

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public BulkInsertService(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public void insertExactlyFiveMillion() {

        String insertSql = """
            INSERT INTO vac (vac, cid, crexpdt, custcod, custnam, dbexpdt, debmax, debmin, eftdt, email, expdt, lstdt, pnrflg, prodid, prodnam, regdt, regtm, sid, uid, vaind, vanam, vastat, vav, vavtb)
            VALUES (?, '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423', '234123423')
            ON CONFLICT (vac) DO NOTHING
        """;

        int insertedSoFar = 0;
        while (insertedSoFar < TARGET_COUNT) {
            System.out.println(insertedSoFar);
            List<Object[]> batch = new ArrayList<>(BATCH_SIZE);

            for (int i = 0; i < BATCH_SIZE; i++) {
                batch.add(new Object[]{CommonUtil.generate()});
            }
            System.out.println(batch);

            int[] result = jdbcTemplate.batchUpdate(insertSql, batch);
//            log.info(Arrays.toString(result));
            insertedSoFar += result.length;
        }
    }

    private int getCurrentCount() {
        return jdbcTemplate.queryForObject("""
                    SELECT reltuples::BIGINT
                    FROM pg_class
                    WHERE relname = 'vac'
                """, Integer.class);
    }
}
