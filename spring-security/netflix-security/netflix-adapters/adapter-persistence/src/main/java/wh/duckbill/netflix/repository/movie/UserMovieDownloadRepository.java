package wh.duckbill.netflix.repository.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.netflix.entity.movie.UserMovieDownloadEntity;
import wh.duckbill.netflix.movie.DownloadMoviePort;
import wh.duckbill.netflix.movie.UserMovieDownload;

@Repository
@RequiredArgsConstructor
public class UserMovieDownloadRepository implements DownloadMoviePort {
  private final UserMovieDownloadJpaRepository userMovieDownloadJpaRepository;

  @Transactional
  @Override
  public void save(UserMovieDownload domain) {
    userMovieDownloadJpaRepository.save(UserMovieDownloadEntity.fromDomain(domain));
  }

  @Transactional(readOnly = true)
  @Override
  public long downloadCntToday(String userId) {
    return userMovieDownloadJpaRepository.countDownloadToday(userId);
  }
}
