package com.example.springexception.advice;

import com.example.springexception.controller.ApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// INFO: basePackages 경로 지정을 통하여 원하는 패키지만 Exception Handle 을 할 수 있다.
// INFO: 지정하지 않을 경우 Global 하게 적용
//@RestControllerAdvice(basePackageClasses = ApiController.class) // 해당하는 Controller 에만 적용
@RestControllerAdvice(basePackages = "com.example.springexception.controller")
public class GlobalControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e){
        System.out.println(e.getClass().getName());
        System.out.println(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e){
        System.out.println("---------------global--------------------");
        System.out.println(e.getClass().getName());
        System.out.println(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
