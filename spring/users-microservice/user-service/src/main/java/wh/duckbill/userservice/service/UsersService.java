package wh.duckbill.userservice.service;

import wh.duckbill.userservice.dto.UserDto;

public interface UsersService {
    UserDto createUser(UserDto userDto);
}
