package com.example.batch.batch;

import com.example.batch.entity.Csv;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvListener {

    // job에서 사용하는 데이터를 보관하는 보관소
    private ExecutionContext executionContext;
    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
       // executionContext = jobExecution.getExecutionContext();

        executionContext.putLong("impCntSum", 0);
    }

    @BeforeWrite
    public void beforeWrite(List<Csv> list) {
        Long sum = executionContext.getLong("impCntSum");

        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getImpCnt();
        }
        executionContext.putLong("impCntSum", sum);
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        ExecutionContext executionContext = jobExecution.getExecutionContext();
        Long Total = executionContext.getLong("impCntSum");

        System.out.println("총 노출 수 ===================== : " + Total);
    }
}
