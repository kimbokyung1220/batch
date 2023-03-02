package com.example.batch.repository;

import com.example.batch.entity.Csv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvRepository extends JpaRepository<Csv, Long> {
}
