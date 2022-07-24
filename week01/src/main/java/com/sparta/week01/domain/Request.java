package com.sparta.week01.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request<T> {
    private boolean success;
    private T data;
    private T error;
}
