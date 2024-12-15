package wh.duckbill.netflix.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private final ErrorCode errorCode;

    public static class UserDoesNotExistException extends UserException {
        public UserDoesNotExistException() {
            super(ErrorCode.USER_DOES_NOT_EXIST);
        }
    }

    public static class UserAlreadyExistsException extends UserException {
        public UserAlreadyExistsException() {
            super(ErrorCode.USER_ALREADY_EXIST);
        }
    }
}
