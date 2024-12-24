package wh.duckbill.netflix.repository.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.netflix.entity.movie.MovieEntity;
import wh.duckbill.netflix.movie.NetflixMovie;
import wh.duckbill.netflix.movie.PersistenceMoviePort;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository implements PersistenceMoviePort {
  private final MovieJpaRepository movieJpaRepository;
  @Transactional(readOnly=true)
  @Override
  public List<NetflixMovie> fetchBy(int page, int size) {
    return movieJpaRepository.search(PageRequest.of(page, size))
        .stream().map(MovieEntity::toDomain)
        .toList();
  }

  @Transactional(readOnly=true)
  @Override
  public NetflixMovie findBy(String movieName) {
    return movieJpaRepository.findByMovieName(movieName)
        .map(MovieEntity::toDomain)
        .orElseThrow();
  }

  @Transactional
  @Override
  public void insert(NetflixMovie netflixMovie) {
    Optional<MovieEntity> byMovieName = movieJpaRepository.findByMovieName(netflixMovie.getMovieName());
    if (byMovieName.isPresent()) {
      return;
    }
    MovieEntity movieEntity = MovieEntity.newEntity(netflixMovie.getMovieName(), netflixMovie.getIsAdult(), netflixMovie.getGenre(), netflixMovie.getOverview(), netflixMovie.getReleaseAt());

    movieJpaRepository.save(movieEntity);
  }
}
