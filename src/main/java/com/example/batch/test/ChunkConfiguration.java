package com.example.batch.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ChunkConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob1() {
        return this.jobBuilderFactory.get("batchJob1")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep1())
                .build();
    }

//    @Bean
//    public Job batchJob2() {
//        return this.jobBuilderFactory.get("batchJob2")
//                .incrementer(new RunIdIncrementer())
//                .start(chunkStep2())
//                .build();
//    }

    @Bean
//    @ConditionalOnProperty(value = "batch.job.name", havingValue = "chunkStep1", matchIfMissing = false)
    public Step chunkStep1() {
        return stepBuilderFactory.get("chunkStep1")
                // <reader, writer>
                .<String, String>chunk(5)
                .reader(new ListItemReader<>(Arrays.asList("item1","item2","item3","item4","item5")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        Thread.sleep(300);
                        return "my " + item;
//                        return item.toUpperCase();
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception { //일괄처리를 위해 List타입의 아이템을 전달받음
                        items.forEach(item -> System.out.println(item));
                    }
                })
                .build();
    }

    @Bean
    public Step chunkStep2() {
        return stepBuilderFactory.get("chunkStep2")
                .<Customer, Customer>chunk(3)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemWriter<? super Customer> itemWriter() {
        return new CustomItemWrite();
    }

    @Bean
    public ItemProcessor<? super Customer,? extends Customer> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemReader<Customer> itemReader() {
        return new CustomItemReader(Arrays.asList(new Customer("user1"), new Customer("user2"), new Customer("user3")));
    }

}