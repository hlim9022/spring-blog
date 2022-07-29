package com.sparta.week01.domain;

import lombok.*;

@Getter
@AllArgsConstructor
public class ResponseTemp<T> {
    private boolean success;
    private T data;
    private Error error;

    @AllArgsConstructor
    @Getter
    static class Error {
        private String code;
        private String message;
    }

    public static <T> ResponseTemp<T> success(T data) {
        return new ResponseTemp<>(true, data, null);
    }

    public static <T> ResponseTemp<T> fail(String code, String message) {
        return new ResponseTemp<>(false, null, new Error(code, message));
    }
}
