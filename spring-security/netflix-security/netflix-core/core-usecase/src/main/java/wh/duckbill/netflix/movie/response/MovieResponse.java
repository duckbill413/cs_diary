package wh.duckbill.netflix.movie.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MovieResponse {
    private final String movieName;
    private final Boolean isAdult;
    private final List<String> genre;
    private final String overview;
    private final String releaseAt;
}
