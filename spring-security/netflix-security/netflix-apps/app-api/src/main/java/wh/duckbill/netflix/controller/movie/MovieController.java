package wh.duckbill.netflix.controller.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.netflix.controller.NetflixApiResponse;
import wh.duckbill.netflix.movie.FetchMovieUsecase;
import wh.duckbill.netflix.movie.response.PageableMovies;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final FetchMovieUsecase fetchMovieUsecase;

    @GetMapping("/api/v1/movie/client/{page}")
    public NetflixApiResponse<PageableMovies> fetchMoviePageables(@PathVariable int page) {
        PageableMovies pageableMovies = fetchMovieUsecase.fetchFromClient(page);
        return NetflixApiResponse.ok(pageableMovies);
    }
}
