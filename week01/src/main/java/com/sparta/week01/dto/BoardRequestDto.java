package com.sparta.week01.dto;


import com.sparta.week01.entity.Comment;
import com.sparta.week01.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;
    private User user;
    private List<Comment> commentList;
}
