package wh.duckbill.netflix.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NetflixMovie {
  private String movieName;
  private Boolean isAdult;
  private String genre;
  private String overview;
  private String releaseAt;

  public NetflixMovie(String movieName, Boolean isAdult, String genre, String overview, String releaseAt) {
    this.movieName = movieName;
    this.isAdult = isAdult;
    this.genre = genre;
    this.overview = overview;
    this.releaseAt = releaseAt;
  }
}
