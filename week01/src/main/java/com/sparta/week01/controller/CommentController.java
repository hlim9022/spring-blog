package com.sparta.week01.controller;


import com.sparta.week01.domain.Comment;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/list/{id}/comments")
    public ResponseDto<?> getAllCommentList(@PathVariable(name = "id") Long boardId) {
        List<Comment> allComments = commentService.getAllComments(boardId);

        return ResponseDto.success(allComments);
    }

    @PostMapping("/auth/list/{id}/comments")
    public ResponseDto<?> addComments(@PathVariable(name = "id") Long boardId,
                                      @RequestBody String comment) {

        return commentService.addComments(boardId, comment);
    }


}
