package wh.duckbill.netflix.controller;

import wh.duckbill.netflix.exception.ErrorCode;

public record NetflixApiResponse<T>(
        boolean success,
        String code,
        String message,
        T data
) {
    public static final String CODE_SUCCESS = "SUCCEED";

    public static <T> NetflixApiResponse<T> ok(T data) {
        return new NetflixApiResponse<T>(true, CODE_SUCCESS, null, data);
    }

    public static <T> NetflixApiResponse<T> fail(ErrorCode errorCode, String message) {
        return new NetflixApiResponse<>(false, errorCode.getCode(), message, null);
    }
}
