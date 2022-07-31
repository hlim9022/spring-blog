package com.sparta.week01.dto;

import com.sparta.week01.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private String comment;
    private Long boardId;
}
