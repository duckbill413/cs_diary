package com.example.batch.part4.batch;

import com.example.batch.TestConfiguration;
import com.example.batch.part4.model.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;


/**
 * author        : duckbill413
 * date          : 2023-02-07
 * description   :
 **/
@SpringBatchTest
@ContextConfiguration(classes = {UsersGradeConfiguration.class, TestConfiguration.class})
class UsersGradeConfigurationTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        int size = usersRepository.findAllByUpdatedDate(LocalDate.now()).size();


        Assertions.assertThat(jobExecution.getStepExecutions().stream()
                .filter(x -> x.getStepName().equals("userLevelUpStep"))
                .mapToInt(StepExecution::getWriteCount)
                .sum())
                .isEqualTo(size)
                .isEqualTo(66);

        Assertions.assertThat(usersRepository.count())
                .isEqualTo(100);
    }
}