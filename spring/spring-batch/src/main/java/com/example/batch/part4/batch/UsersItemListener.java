package com.example.batch.part4.batch;

import com.example.batch.part4.model.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

/**
 * author        : duckbill413
 * date          : 2023-02-07
 * description   :
 **/
@Slf4j
@RequiredArgsConstructor
public class UsersItemListener {
    private final UsersRepository usersRepository;
    @AfterJob
    public void showJobResult(JobExecution jobExecution){
        Collection<Object> users = usersRepository.findAllByUpdatedDate(LocalDate.now());

        Date startTime = jobExecution.getStartTime();
        Date endTime = jobExecution.getEndTime();
        long totalTime = endTime.getTime() - startTime.getTime();

        log.info("총 데이터 처리 {}건, 처리 시간 : {}millis", users.size(), totalTime);
    }
}
