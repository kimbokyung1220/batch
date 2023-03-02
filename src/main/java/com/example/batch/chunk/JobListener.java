package com.example.batch.chunk;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {

        jobExecution.getExecutionContext().putString("period", "20220515");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(" ######### 20220515 insert 완 ###########");
    }
}
