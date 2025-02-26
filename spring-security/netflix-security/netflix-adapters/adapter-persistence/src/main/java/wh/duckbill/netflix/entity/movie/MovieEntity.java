package wh.duckbill.netflix.entity.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import wh.duckbill.netflix.audit.MutableBaseEntity;
import wh.duckbill.netflix.movie.NetflixMovie;

import java.util.UUID;

@Getter
@Entity
@Table(name = "movies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MovieEntity extends MutableBaseEntity {
  @Id
  @Column(name = "MOVIE_ID")
  private String movieId;
  @Column(name = "MOVIE_NAME")
  private String movieName;
  @Column(name = "IS_ADULT")
  private Boolean isAdult;
  @Column(name = "GENRE")
  private String genre;
  @Column(name = "OVERVIEW")
  private String overview;
  @Column(name = "RELEASED_AT")
  private String releaseAt;

  public NetflixMovie toDomain() {
    return NetflixMovie.builder()
        .movieName(movieName)
        .isAdult(isAdult)
        .genre(genre)
        .overview(overview)
        .releaseAt(releaseAt)
        .build();
  }

  public static MovieEntity newEntity(String movieName, Boolean isAdult, String genre, String overview, String releaseAt) {
    return new MovieEntity(
        UUID.randomUUID().toString(),
        movieName,
        isAdult,
        genre,
        getSubstrOverview(overview),
        releaseAt
    );
  }

  private static String getSubstrOverview(String overview) {
    if (StringUtils.isBlank(overview)) {
      return "별도의 서명이 존재하지 않습니다.";
    }
    return overview.substring(0, Math.min(overview.length(), 200));
  }
}
