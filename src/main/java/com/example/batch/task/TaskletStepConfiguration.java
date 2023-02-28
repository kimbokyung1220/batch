package com.example.batch.task;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@RequiredArgsConstructor
@Configuration
public class TaskletStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(taskStep1())
                .next(taskStep2())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) { //사전 작업
                        
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) { //사후 작업
                        return null;
                    }
                })
                .next(taskStep3())
                .build();
    }

    @Bean
    public Step taskStep1() {
        return stepBuilderFactory.get("taskStep1")
                .tasklet(new Tasklet() { //Tasklet 객체 생성
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("====== taskStep 실행 ======="); //execute메서드 안에 비지니스 로직 작성
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step taskStep2() {
        return stepBuilderFactory.get("taskStep2")
                .tasklet(new CustomTasklet())
                .build();
    }

    @Bean
    public Step taskStep3() {
        return stepBuilderFactory.get("taskStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("stepContribution = " + contribution + ", chunkContext = " + chunkContext);
                        throw new RuntimeException("taskStep3 was fail");
//                        return RepeatStatus.FINISHED;
                    }
                })
                .startLimit(3)
                .build();
    }
}
