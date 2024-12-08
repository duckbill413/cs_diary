package wh.duckbill.netflix.movie.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageableMovies {
    private final List<MovieResponse> movies;
    private final int page;
    private final boolean hasNext;
}
