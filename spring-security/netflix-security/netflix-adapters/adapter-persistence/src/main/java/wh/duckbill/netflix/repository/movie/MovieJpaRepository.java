package wh.duckbill.netflix.repository.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.movie.MovieEntity;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, String>, MovieCustomRepository {
}
