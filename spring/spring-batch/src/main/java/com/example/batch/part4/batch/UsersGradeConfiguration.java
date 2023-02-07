package com.example.batch.part4.batch;

import com.example.batch.part4.model.Level;
import com.example.batch.part4.model.Users;
import com.example.batch.part4.model.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-02-07
 * description   :
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class UsersGradeConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final UsersRepository usersRepository;

    @Bean
    public Job usersGradeJob() throws Exception {
        return this.jobBuilderFactory.get("usersGradeJob")
                .incrementer(new RunIdIncrementer())
                .start(this.saveUserStep())
                .next(this.userLevelUpStep())
                .listener(new UsersGradeItemListener(usersRepository))
                .build();
    }
    @Bean
    public Step saveUserStep() {
        return this.stepBuilderFactory.get("saveUserStep")
                .tasklet(new SaveUsersTasklet(usersRepository))
                .build();
    }
    @Bean
    public Step userLevelUpStep() throws Exception {
        return this.stepBuilderFactory.get("userLevelUpStep")
                .<Users, Users>chunk(10)
                .reader(this.loadUsersData())
                .processor(this.checkUsersData())
                .writer(this.fixUsersGradeData())
                .build();
    }

    private ItemReader<? extends Users> loadUsersData() throws Exception {
        JpaPagingItemReader<Users> jpaPagingItemReader = new JpaPagingItemReaderBuilder<Users>()
                .name("loadUsersData")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("select u from Users u")
                .build();
        jpaPagingItemReader.afterPropertiesSet();
        return jpaPagingItemReader;
    }

    private ItemProcessor<? super Users,? extends Users> checkUsersData() {
        return user -> {
            if (user.availableLevelUp())
                return user;

            return null;
        };
    }
    private ItemWriter<? super Users> fixUsersGradeData() throws Exception {
        return users -> users.forEach(user -> {
            user.levelUp();
            usersRepository.save(user);
        });
    }
}
