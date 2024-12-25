package wh.duckbill.netflix.movie;

import wh.duckbill.netflix.movie.response.PageableMoviesResponse;

public interface FetchMovieUsecase {
    PageableMoviesResponse fetchFromClient(int page);
    PageableMoviesResponse fetchFromDb(int page);
}
