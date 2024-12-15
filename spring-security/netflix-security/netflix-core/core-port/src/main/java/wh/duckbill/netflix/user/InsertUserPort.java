package wh.duckbill.netflix.user;

public interface InsertUserPort {
  UserPortResponse create(CreateUser createUser);
}
