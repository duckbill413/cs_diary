package wh.duckbill.netflix.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.movie.response.MovieResponse;
import wh.duckbill.netflix.movie.response.PageableMovies;

@Service
@RequiredArgsConstructor
public class MovieService implements FetchMovieUsecase {
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
}
