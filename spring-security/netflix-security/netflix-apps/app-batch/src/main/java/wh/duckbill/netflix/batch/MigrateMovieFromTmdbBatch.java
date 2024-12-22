package wh.duckbill.netflix.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import wh.duckbill.netflix.movie.FetchMovieUsecase;
import wh.duckbill.netflix.movie.InsertMovieUsecase;
import wh.duckbill.netflix.movie.response.MovieResponse;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MigrateMovieFromTmdbBatch {

  private final static String BATCH_NAME = "MigrateMoviesFromTmdbBatch";

  private final FetchMovieUsecase fetchMovieUseCase;
  private final InsertMovieUsecase insertMovieUseCase;

  @Bean(name = BATCH_NAME)
  public Job job(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new JobBuilder(BATCH_NAME, jobRepository)
        .preventRestart()
        .start(step(jobRepository, platformTransactionManager))
        .incrementer(new RunIdIncrementer())
        .build();
  }

  @Bean(name = "MigrateMoviesFromTmdbBatchTaskletStep")
  public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("MigrateMoviesFromTmdbBatchTaskletStep", jobRepository)
        .chunk(10, platformTransactionManager)
        .reader(new HttpPageItemReader(1, fetchMovieUseCase))
        .writer(chunk -> {
          List<MovieResponse> items = (List<MovieResponse>) chunk.getItems();
          insertMovieUseCase.insert(items);
        })
        .build();
  }
}
