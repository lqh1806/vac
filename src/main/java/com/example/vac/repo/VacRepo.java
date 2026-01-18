package com.example.vac.repo;

import com.example.vac.entity.Vac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacRepo extends JpaRepository<Vac, String> {
    @Query(
            value = """
            SELECT vac
            FROM vac
            LIMIT :limit OFFSET :offset
        """,
            nativeQuery = true
    )
    List<String> findByPaging(
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(
            value = """
            SELECT *
            FROM vac
            LIMIT :limit OFFSET :offset
        """,
            nativeQuery = true
    )
    List<Vac> findFullByPaging(
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """

            select count(vac) from vac;
    
        """, nativeQuery = true)
    long countRecords();
}
