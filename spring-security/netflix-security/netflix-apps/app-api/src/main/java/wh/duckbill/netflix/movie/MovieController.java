package wh.duckbill.netflix.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.netflix.movie.response.PageableMovies;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final FetchMovieUsecase fetchMovieUsecase;

    @GetMapping("/api/v1/movie/client/{page}")
    public String fetchMoviePageables(@PathVariable int page) {
        PageableMovies pageableMovies = fetchMovieUsecase.fetchFromClient(page);
        return "";
    }
}
