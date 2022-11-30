package com.example.springvalidationuses.advice;

import com.example.springvalidationuses.controller.ApiController;
import com.example.springvalidationuses.dto.Error;
import com.example.springvalidationuses.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// INFO: basePackages 경로 지정을 통하여 원하는 패키지만 Exception Handle 을 할 수 있다.
// INFO: 지정하지 않을 경우 Global 하게 적용
@RestControllerAdvice(basePackageClasses = ApiController.class) // 해당하는 Controller 에만 적용
public class ApiControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e) {
        System.out.println(e.getClass().getName());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                          HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();

        System.out.println("MethodArgumentNotValidException Error");
        bindingResult.getAllErrors().forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;

            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            String value = fieldError.getRejectedValue().toString();

            System.out.println(fieldName + "\t" + message + "\t" + value);

            Error error = new Error(fieldName, message, value);
            errorList.add(error);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("");
        errorResponse.setCode("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");
        errorResponse.setErrorList(errorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e,
                                                       HttpServletRequest httpServletRequest) {
        System.out.println("ConstraintViolationException Error");
        List<Error> errorList = new ArrayList<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            Stream<Path.Node> stream = StreamSupport.stream(constraintViolation.getPropertyPath().spliterator(), false);
            List<Path.Node> list = stream.collect(Collectors.toList());

            String fieldName = list.get(list.size()-1).getName();
            String message = constraintViolation.getMessage();
            String invalidValue = constraintViolation.getInvalidValue().toString();
            System.out.println(fieldName + "\t" + message + "\t" + invalidValue);

            Error error = new Error(fieldName, message, invalidValue);
            errorList.add(error);
        });
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("");
        errorResponse.setCode("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");
        errorResponse.setErrorList(errorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                  HttpServletRequest httpServletRequest) {
        System.out.println("MissingServletRequestParameterException");

        List<Error> errorList = new ArrayList<>();

        String fieldName = e.getParameterName();
        String fieldType = e.getParameterType();
        String invalidValue = e.getMessage();

        System.out.println(fieldName + "\t" + fieldType + "\t" + invalidValue);
        errorList.add(new Error(fieldName, fieldType, invalidValue));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("");
        errorResponse.setCode("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");
        errorResponse.setErrorList(errorList);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
