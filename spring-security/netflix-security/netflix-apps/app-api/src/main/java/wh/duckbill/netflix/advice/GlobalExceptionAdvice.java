package wh.duckbill.netflix.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wh.duckbill.netflix.controller.NetflixApiResponse;
import wh.duckbill.netflix.exception.ErrorCode;
import wh.duckbill.netflix.exception.UserException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(UserException.class)
    protected NetflixApiResponse<?> handleUserException(UserException e) {
        log.error("error={}", e.getMessage(), e);
        return NetflixApiResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    protected NetflixApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("error={}", e.getMessage(), e);
        return NetflixApiResponse.fail(ErrorCode.DEFAULT_ERROR, e.getMessage());
    }
}
