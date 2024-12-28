package wh.duckbill.netflix.entity.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wh.duckbill.netflix.audit.MutableBaseEntity;
import wh.duckbill.netflix.movie.UserMovieLike;

@Getter
@Entity
@Table(name = "user_movie_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserMovieLikeEntity extends MutableBaseEntity {
  @Id
  @Column(name = "USER_MOVIE_LIKE_ID")
  private String userMovieLikeId;
  @Column(name = "USER_ID")
  private String userId;
  @Column(name = "MOVIE_ID")
  private String movieId;
  @Column(name = "LIKE_YN")
  private Boolean likeYn;

  public UserMovieLike toDomain() {
    return UserMovieLike.builder()
        .userMovieLikeId(userMovieLikeId)
        .userId(userId)
        .movieId(movieId)
        .likeYn(likeYn).build();
  }

  public static UserMovieLikeEntity fromDomain(UserMovieLike userMovieLike) {
    return new UserMovieLikeEntity(
        userMovieLike.getUserMovieLikeId(),
        userMovieLike.getUserId(),
        userMovieLike.getMovieId(),
        userMovieLike.getLikeYn()
    );
  }
}
