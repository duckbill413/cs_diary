package wh.duckbill.netflix.repository.movie;

public interface UserMovieDownloadCustomRepository {
  long countDownloadToday(String userId);
}
