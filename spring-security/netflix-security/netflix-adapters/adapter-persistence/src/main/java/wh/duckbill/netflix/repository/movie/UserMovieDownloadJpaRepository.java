package wh.duckbill.netflix.repository.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.movie.UserMovieDownloadEntity;

public interface UserMovieDownloadJpaRepository extends JpaRepository<UserMovieDownloadEntity, String>, UserMovieDownloadCustomRepository {
}
