package wh.duckbill.netflix.controller.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wh.duckbill.netflix.controller.NetflixApiResponse;
import wh.duckbill.netflix.movie.FetchMovieUsecase;
import wh.duckbill.netflix.movie.response.PageableMoviesResponse;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final FetchMovieUsecase fetchMovieUsecase;

    @GetMapping("/api/v1/movie/client/{page}")
    public NetflixApiResponse<PageableMoviesResponse> fetchMoviePageables(@PathVariable int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUsecase.fetchFromClient(page);
        return NetflixApiResponse.ok(pageableMoviesResponse);
    }

    @PostMapping("/api/v1/movie/search")
    public NetflixApiResponse<PageableMoviesResponse> search(@RequestParam int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUsecase.fetchFromClient(page);
        return NetflixApiResponse.ok(pageableMoviesResponse);
    }
}
