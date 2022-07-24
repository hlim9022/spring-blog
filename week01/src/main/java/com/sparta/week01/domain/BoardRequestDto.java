package com.sparta.week01.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardRequestDto {
    private String title;
    private String contents;
    private String author;
    private int password;
}
