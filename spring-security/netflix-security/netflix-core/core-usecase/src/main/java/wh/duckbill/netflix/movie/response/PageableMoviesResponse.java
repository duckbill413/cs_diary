package wh.duckbill.netflix.movie.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageableMoviesResponse {
    private final List<MovieResponse> movieResponses;
    private final int page;
    private final boolean hasNext;
}
