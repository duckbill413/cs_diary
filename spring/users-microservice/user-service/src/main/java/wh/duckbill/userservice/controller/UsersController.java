package wh.duckbill.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wh.duckbill.userservice.dto.UserDto;
import wh.duckbill.userservice.service.UsersService;
import wh.duckbill.userservice.vo.Greeting;
import wh.duckbill.userservice.vo.RequestUser;
import wh.duckbill.userservice.vo.ResponseUser;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final Greeting greeting;

    @GetMapping("/health-check")
    public String status() {
        return "It's working in user service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        usersService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
