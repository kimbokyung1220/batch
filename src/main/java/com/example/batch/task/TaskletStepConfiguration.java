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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class TaskletStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob") // JobBuilder 를 생성하는 팩토리,  Job 의 이름을 매개변수로 받음
                .incrementer(new RunIdIncrementer()) // JobParameter의 값을 자동으로 증가
                .start(taskStep4()) // 처음 실행 할 step, 최초 한번 설정, 이 메서드를 실행하면 SimpleJobBuilder 반환
//                .next(taskStep2()) // 다음에 실행 할 step, 횟수 제한 X , 모든 next()의 step이 종료되면 job 종료
//                .next(taskStep3())
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
//                        throw new RuntimeException("taskStep3 was fail");
                        return RepeatStatus.FINISHED;
                    }
                })
                .startLimit(3)
                .build();
    }

    @Bean
    public Step taskStep4() {
        return stepBuilderFactory.get("taskStep4")
                .tasklet(tasklet())
                .build();
    }

    private Tasklet tasklet() { //tasklet으로 모두 처리
        return (contribution, chunkContext) -> {
            List<String> items = getItems();
            System.out.println("items : " + items.toString());

            return RepeatStatus.FINISHED;
        };
    }

    private List<String> getItems() {
        List<String> items = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            items.add(i + " test!");
        }

        return items;
    }


    @Bean
    public Step taskStep5() {
        return stepBuilderFactory.get("taskStep5")
                .tasklet((contribution, chunkContext) -> {
                            System.out.println("taskStep4 ************ ");
                            return RepeatStatus.FINISHED;
                        }

                )
                .allowStartIfComplete(true)//
                .build();
    }
}
