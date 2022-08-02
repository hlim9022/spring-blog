package com.sparta.week01.dto;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDto {
    private String comment;
    private Board board;
    private User user;
}
