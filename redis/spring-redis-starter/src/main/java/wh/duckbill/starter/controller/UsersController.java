package wh.duckbill.starter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.starter.dto.UserProfile;
import wh.duckbill.starter.service.UsersService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/{userId}/profile")
    public UserProfile getUserProfile(@PathVariable String userId) {
        return usersService.getUserProfile(userId);
    }
}
