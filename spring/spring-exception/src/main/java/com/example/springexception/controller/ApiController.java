package com.example.springexception.controller;

import com.example.springexception.dto.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("")
    public User get(@RequestParam(required = false) String name,
                    @RequestParam(required = false) Integer age){
        User user = new User();
        user.setName(name);
        user.setAge(age);
        System.out.println(user);

        int testAge = age + 10;

        return user;
    }

    @PostMapping("")
    public User post(@Valid @RequestBody User user){
        System.out.println(user);
        return user;
    }

    // INFO: ExceptionHandler 를 Controller 안에 지정하면 해당 Controller 에 대해서만 작동
    // INFO: Global Exception Handler 는 작동되지 않는다.
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e){
        System.out.println("------------ApiController Local----------------");
        System.out.println(e.getClass().getName());
        System.out.println(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
