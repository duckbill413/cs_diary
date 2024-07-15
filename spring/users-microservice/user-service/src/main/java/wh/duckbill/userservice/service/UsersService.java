package wh.duckbill.userservice.service;

import wh.duckbill.userservice.dto.UserDto;
import wh.duckbill.userservice.jpa.UserEntity;

public interface UsersService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();
}
