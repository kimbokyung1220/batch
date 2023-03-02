package com.example.batch.chunk;

import com.example.batch.entity.Csv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private static final int chunkSize = 1000;

    @Bean
    public Job csvFileJob() {
        return jobBuilderFactory.get("csvFileJob")
                .start(csvFileStep())
                .build();
    }

    @Bean
    public Step csvFileStep() {
        return stepBuilderFactory.get("csvFileStep")
                .<Csv, Csv>chunk(chunkSize)
                .reader(csvReader.csvFileReader())
                .writer(csvWriter)
                .build();
    }
}
