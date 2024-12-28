package wh.duckbill.netflix.movie;

public interface DownloadMoviePort {
  void save(UserMovieDownload domain);

  long downloadCntToday(String userId);
}
