package wh.duckbill.netflix.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.movie.response.MovieResponse;
import wh.duckbill.netflix.movie.response.PageableMoviesResponse;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService implements FetchMovieUsecase, InsertMovieUsecase {
  private final TmdbMoviePort tmdbMoviePort;
  private final PersistenceMoviePort persistenceMoviePort;

  @Override
  public PageableMoviesResponse fetchFromClient(int page) {
    TmdbPageableMovies tmdbPageableMovies = tmdbMoviePort.fetchPageable(page);
    return new PageableMoviesResponse(
        tmdbPageableMovies.getTmdbMovies().stream()
            .map(movie -> new MovieResponse(
                movie.getMovieName(),
                movie.getIsAdult(),
                movie.getGenre(),
                movie.getOverview(),
                movie.getReleaseAt())
            ).toList(),
        tmdbPageableMovies.getPage(),
        tmdbPageableMovies.isHasNext()
    );
  }

  @Override
  public PageableMoviesResponse fetchFromDb(int page) {
    List<NetflixMovie> netflixMovies = persistenceMoviePort.fetchBy(page, 10);
    return new PageableMoviesResponse(
        netflixMovies.stream().map(it -> new MovieResponse(it.getMovieName(), it.getIsAdult(), List.of(), it.getOverview(), it.getReleaseAt())).toList(),
        page,
        true
    );
  }

  @Override
  public void insert(List<MovieResponse> items) {
    items.forEach(it -> persistenceMoviePort.insert(
        NetflixMovie.builder()
            .movieName(it.getMovieName())
            .isAdult(it.getIsAdult())
            .genre("")
            .overview(it.getOverview())
            .releaseAt(it.getReleaseAt())
            .build()
    ));
  }
}
