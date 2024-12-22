package wh.duckbill.netflix.batch;

import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import wh.duckbill.netflix.movie.FetchMovieUsecase;
import wh.duckbill.netflix.movie.response.MovieResponse;
import wh.duckbill.netflix.movie.response.PageableMovies;

import java.util.LinkedList;
import java.util.List;

public class HttpPageItemReader extends AbstractItemCountingItemStreamItemReader<MovieResponse> {
  private int page;
  private final List<MovieResponse> contents = new LinkedList<>();
  private final FetchMovieUsecase fetchMovieUsecase;

  public HttpPageItemReader(int page, FetchMovieUsecase fetchMovieUsecase) {
    this.page = page;
    this.fetchMovieUsecase = fetchMovieUsecase;
  }
  @Override
  protected MovieResponse doRead() throws Exception {
    if (contents.isEmpty()) {
      readRow();
    }

    int size = contents.size();
    int index = size - 1;

    if (index < 0) {
      return null;
    }
    return contents.remove(contents.size() - 1);
  }

  private void readRow() {
    PageableMovies pageableMovies = fetchMovieUsecase.fetchFromClient(page);
    contents.addAll(pageableMovies.getMovieResponses());
    page++;
  }

  @Override
  protected void doOpen() throws Exception {
    setName(HttpPageItemReader.class.getName());
  }

  @Override
  protected void doClose() throws Exception {

  }
}
