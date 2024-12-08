package wh.duckbill.netflix.movie;

import wh.duckbill.netflix.movie.response.PageableMovies;

public interface FetchMovieUsecase {
    PageableMovies fetchFromClient(int page);
}
