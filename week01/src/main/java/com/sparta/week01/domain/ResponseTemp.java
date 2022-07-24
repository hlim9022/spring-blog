package com.sparta.week01.domain;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemp<T> {
    private boolean success;
    private T data;
    private Map<String, String> error;


    public ResponseTemp<T> getHttpResponseTemp(T data) {
        ResponseTemp<T> res = new ResponseTemp<>();
        res.setSuccess(HttpStatus.OK.is2xxSuccessful());
        res.setData(data);
        res.setError(res.getError());
        return res;
    }
}
