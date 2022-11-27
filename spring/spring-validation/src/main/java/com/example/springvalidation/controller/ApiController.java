package com.example.springvalidation.controller;

import com.example.springvalidation.dto.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    @PostMapping("/user")
    public User user(@RequestBody User user) {
        System.out.println(user);
        return user;
    }

    @PostMapping("/user/validation/response-entity")
    public ResponseEntity<User> userValidation(@Valid @RequestBody User user){
        System.out.println(user);

        if (user.getAge() >= 90)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/validation/binding-result")
    public ResponseEntity userBindingResult(@Valid @RequestBody User user,
                                                  BindingResult bindingResult){
        
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                String message = fieldError.getDefaultMessage();
                sb.append(fieldError.getField() + "\t");
                sb.append(message + "\n");
            });
            System.out.println(sb);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }

        System.out.println(user);

        return ResponseEntity.ok(user);
    }
}