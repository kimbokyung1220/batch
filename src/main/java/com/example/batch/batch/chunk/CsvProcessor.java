package com.example.batch.batch.chunk;

import com.example.batch.entity.Csv;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class CsvProcessor implements ItemProcessor<Csv, Csv> {
    private final String period;
    @Override
    public Csv process(Csv csv) throws Exception {
        if( period != null && !period.equals(csv.getPeriod())) {
            return null;
        }
        return csv;
    }
}
