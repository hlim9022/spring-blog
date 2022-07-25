package com.sparta.week01.domain;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemp<T> {
    private boolean success;
    private T data;
    private Map<String, String> error;


    public ResponseTemp<T> getHttpResponseTemp(int httpCode, T data) {

        if(httpCode == 200) {
            setSuccess(true);
            setData(data);
            setError(null);
        } else if (httpCode == 404){
            setSuccess(false);
            setData(null);
            setError(new HashMap<>(){{
                put("code","NULL_POST_ID");
                put("message","post id does not exist.");
            }});
        } else if (httpCode == 400) {
            setSuccess(false);
            setData(null);
            setError(new HashMap<>(){{
                put("code","ACCESS_DENIED");
                put("message","password is wrong");
            }});
        }
        return this;
    }
}
