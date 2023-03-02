package com.example.batch.chunk;

import com.example.batch.entity.Csv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //  JPA를 사용하기 위해 EntityManagerFactory를 주입받아야 함
    private final EntityManagerFactory entityManagerFactory;
    private static final int chunkSize = 1000;

    @Bean
    public Job csvFileJob() {
        return jobBuilderFactory.get("csvFileJob")
                .start(simpleStep1(null)) // 호출부에서는 null로 처리
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[period]}") String period) {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step1");
                    log.info(">>>>> requestDate = {}", period);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
//    @Bean
//    @JobScope
//    public Step csvFileStep(@Value("#{jobParameters[period]}") String period) {
//        return stepBuilderFactory.get("csvFileStep")
//                .<Csv, Csv>chunk(chunkSize)
//                .reader(csvFileReader(null))
//                .writer(csvFileWriter())
//                .build();
//    }

    /**
     * File Read
     */
    public FlatFileItemReader<Csv> csvFileReader() {

        return new FlatFileItemReaderBuilder<Csv>()
                .name("csvFileReader")
                .resource(new ClassPathResource("/file/sample_encoding.csv"))
                .linesToSkip(1)
                .encoding("UTF-8")
                .delimited().delimiter(",")
                .names("afCode", "afNm", "costSource", "adType", "campaign", "subCampaign", "device", "channel", "mediaNm", "productNm", "brand", "brandNum", "departmentNm", "keyword", "period", "impCnt")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Csv>() {{
                    setTargetType(Csv.class);
                }})
                .build();
    }

    public JpaItemWriter<Csv> csvFileWriter() {
       return new JpaItemWriterBuilder<Csv>()
                .entityManagerFactory(entityManagerFactory)
                .build();

    }


}
