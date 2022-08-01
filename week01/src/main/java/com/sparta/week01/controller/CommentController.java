package com.sparta.week01.controller;


import com.sparta.week01.dto.CommentDto;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 목록조회(권한 필요X)
    @GetMapping("/list/{id}/comments")
    public ResponseDto<?> getAllCommentList(@PathVariable(name = "id") Long boardId) {
        return commentService.getAllComments(boardId);
    }

    // 댓글 작성(권한 필요)
    @PostMapping("/auth/list/{id}/comments")
    public ResponseDto<?> addComments(@PathVariable(name = "id") Long boardId,
                                      @RequestBody CommentDto commentDto) {

        return commentService.addComments(boardId, commentDto);
    }


    // 댓글 수정(권한 필요)
    @PutMapping("/auth/list/{id}/comments/{commentId}")
    public ResponseDto<?> modifyComment(@PathVariable(name = "id") Long boardId,
                                        @PathVariable(name = "commentId") Long commentId,
                                        @RequestBody CommentDto commentDto) {
        return commentService.updateComment(boardId, commentId, commentDto);
    }


    // 댓글 삭제(권한 필요)
    @DeleteMapping("/auth/list/{id}/comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable(name = "id") Long boardId,
                                        @PathVariable(name = "commentId") Long commentId) {
        return commentService.removeComment(boardId, commentId);
    }


}
