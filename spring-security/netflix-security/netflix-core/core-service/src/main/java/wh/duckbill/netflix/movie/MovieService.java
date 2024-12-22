package wh.duckbill.netflix.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.movie.response.MovieResponse;
import wh.duckbill.netflix.movie.response.PageableMovies;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService implements FetchMovieUsecase, InsertMovieUsecase {
    private final TmdbMoviePort tmdbMoviePort;

    @Override
    public PageableMovies fetchFromClient(int page) {
        TmdbPageableMovies tmdbPageableMovies = tmdbMoviePort.fetchPageable(page);
        return new PageableMovies(
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
    public void insert(List<MovieResponse> items) {
        log.info("[{}] {}", items.size(), items.get(0).getMovieName());
    }
}
