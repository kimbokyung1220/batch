package com.example.batch.test;

import com.example.batch.batch.CsvConfiguration;
import com.example.batch.entity.Csv;
import com.example.batch.repository.CsvRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
//@SpringBootTest(classes = {ChunkConfiguration.class, TestBatchConfig.class})
@SpringBootTest(classes = {CsvConfiguration.class, TestBatchConfig.class})
class ChunkJobConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils; // launchJob, laucnStep()과 같은 스프링 배치 테스트에 필요한 유틸성 메서드 지원

    @Test
    @DisplayName("processer 파라미터 테스트")
    void 파라미터_테스트() throws Exception {

        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("period", "20220513")
                .toJobParameters();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
        Assert.assertEquals(jobExecution.getExitStatus(), BatchStatus.COMPLETED);
    }

    //    @Test
//    void chunkJob() throws Exception {
//
//        // when
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
//
//        // then
//        Assertions.assertThat(jobExecution.getStatus())
//                .isEqualTo(BatchStatus.COMPLETED);
//        Assertions.assertThat(jobExecution.getExitStatus())
//                .isEqualTo(ExitStatus.COMPLETED);
//
//    }
}