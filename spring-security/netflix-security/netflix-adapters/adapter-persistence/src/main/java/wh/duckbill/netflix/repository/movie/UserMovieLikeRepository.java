package wh.duckbill.netflix.repository.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wh.duckbill.netflix.entity.movie.UserMovieLikeEntity;
import wh.duckbill.netflix.movie.LikeMoviePort;
import wh.duckbill.netflix.movie.UserMovieLike;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserMovieLikeRepository implements LikeMoviePort {
  private final UserMovieLikeJpaRepository userMovieLikeJpaRepository;

  @Override
  public void save(UserMovieLike domain) {
    userMovieLikeJpaRepository.save(UserMovieLikeEntity.fromDomain(domain));
  }

  @Override
  public Optional<UserMovieLike> findByUserIdAndMovieId(String userId, String movieId) {
    return userMovieLikeJpaRepository.findByUserIdAndMovieId(userId, movieId)
        .map(UserMovieLikeEntity::toDomain);
  }
}
