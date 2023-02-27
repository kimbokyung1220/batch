package com.example.batch;

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

@Configuration
@RequiredArgsConstructor
public class DBJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepTaskletTestJob() {
        return this.jobBuilderFactory.get("stepTaskletTestJob")
                /* 각각의 스텝은 독립적으로 생성되어 Tasklet 이 생성된다.
                 * Step 은 자기만의 Tasklet 을 가진다. */
                .start(taskletTestStep1())
                .next(taskletTestStep2())
                .build();
    }

    @Bean
    public Step taskletTestStep1() {
        return stepBuilderFactory.get("taskletTestStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("taskletTestStep1 ============== ");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step taskletTestStep2() {
        return stepBuilderFactory.get("taskletTestStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("taskletTestStep2 =========== ");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
