package com.example.batch.chunk;

import com.example.batch.dto.CsvDto;
import com.example.batch.entity.Csv;
import com.example.batch.repository.CsvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<Csv> {
    private final CsvRepository csvRepository;

    @Override
    public void write(List<? extends Csv> list) throws Exception {
        csvRepository.saveAll(new ArrayList<Csv>(list));
    }
}
