package com.example.batch.task;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
                .start(taskStep())
                .build();
    }
    @Bean
    public Step taskStep() {
        return stepBuilderFactory.get("taskStep")
                .tasklet(new Tasklet() { //Tasklet 객체 생성
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("====== taskStep 실행 ======="); //execute메서드 안에 비지니스 로직 작성
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

}
