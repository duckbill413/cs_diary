package com.example.batch.part3.FileItemProcessor;

import com.example.batch.part3.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author        : duckbill413
 * date          : 2023-01-31
 * description   :
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SavePersonConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    @Bean
    public Job savePersonJob() throws Exception {
        return this.jobBuilderFactory.get("SavePersonJob")
                .incrementer(new RunIdIncrementer())
                .start(this.savePersonStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step savePersonStep(@Value("#{jobParameters[allow_duplicate]}") Boolean allowDuplicate) throws Exception {
        return this.stepBuilderFactory.get("SavePersonStep")
                .<Person, Person>chunk(10)
                .reader(this.itemReader())
                .processor(new DuplicateValidationProcessor<>(Person::getName, allowDuplicate))
                .writer(this.compositeItemWriter())
                .build();
    }

    public CompositeItemWriter<Person> compositeItemWriter() {
        CompositeItemWriter<Person> compositeItemWriter = new CompositeItemWriterBuilder<Person>()
                .delegates(itemWriter(), itemLogWriter())
                .build();
        return compositeItemWriter;
    }

    private ItemWriter<? super Person> itemWriter() {
        JdbcBatchItemWriter<Person> personJdbcBatchItemWriter = new JdbcBatchItemWriterBuilder<Person>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into person(name, age, address) values(:name, :age, :address)")
                .build();
        personJdbcBatchItemWriter.afterPropertiesSet();

        return personJdbcBatchItemWriter;
    }

    private ItemWriter<? super Person> itemLogWriter() {
        return items -> log.info("person size: {}", items.size());
    }

    private FlatFileItemReader<Person> itemReader() throws Exception {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "name", "age", "address");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            int id = fieldSet.readInt("id");
            String name = fieldSet.readString("name");
            String age = fieldSet.readString("age");
            String address = fieldSet.readString("address");

            return new Person(id, name, age, address);
        });

        FlatFileItemReader<Person> personItemReader = new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .encoding("UTF-8")
                .resource(new FileSystemResource("output/test-input.csv"))
                .linesToSkip(1)
                .lineMapper(lineMapper)
                .build();
        personItemReader.afterPropertiesSet();
        return personItemReader;
    }
}
