package wh.duckbill.netflix.movie;

import java.util.List;

public interface PersistenceMoviePort {
  List<NetflixMovie> fetchBy(int page, int size);

  NetflixMovie findBy(String movieName);

  void insert(NetflixMovie netflixMovie);
}
