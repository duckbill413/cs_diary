package wh.duckbill.netflix.repository.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.movie.UserMovieLikeEntity;

import java.util.Optional;

public interface UserMovieLikeJpaRepository extends JpaRepository<UserMovieLikeEntity, String> {
  Optional<UserMovieLikeEntity> findByUserIdAndMovieId(String userId, String movieId);
}
