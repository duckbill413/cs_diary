package wh.duckbill.netflix.movie;

public interface TmdbMoviePort {
    TmdbPageableMovies fetchPageable(int page);
}
