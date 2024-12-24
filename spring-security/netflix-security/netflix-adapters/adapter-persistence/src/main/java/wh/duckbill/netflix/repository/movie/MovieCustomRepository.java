package wh.duckbill.netflix.repository.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wh.duckbill.netflix.entity.movie.MovieEntity;

import java.util.Optional;

public interface MovieCustomRepository {
  Optional<MovieEntity> findByMovieName(String movieName);

  Page<MovieEntity> search(Pageable pageable);
}
