package wh.duckbill.netflix.movie;

import wh.duckbill.netflix.movie.response.MovieResponse;

import java.util.List;

public interface InsertMovieUsecase {
  void insert(List<MovieResponse> items);
}
