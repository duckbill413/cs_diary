package wh.duckbill.netflix.user;

import wh.duckbill.netflix.user.command.UserResponse;

public interface FetchUserUsecase {
    UserResponse findUserByEmail(String email);
}
