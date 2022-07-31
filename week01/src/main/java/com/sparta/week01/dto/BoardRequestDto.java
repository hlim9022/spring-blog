package com.sparta.week01.dto;


import com.sparta.week01.domain.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardRequestDto {
    private String title;
    private String contents;
    private String author;
    private String password;
    private List<Comment> commentList;
}
