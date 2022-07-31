package com.sparta.week01.controller;

import com.sparta.week01.dto.BoardRequestDto;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/blog")
public class BoardController {
    private final BoardService boardService;


    //전체 블로그글 목록 조회
    @GetMapping("/list")
    public ResponseDto<?> getBlogList() {
        return boardService.getAllBlogList();
    }

    //글작성
    @PostMapping("/auth/list")
    public ResponseDto<?> createBlog(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBlog(requestDto);
    }

    //글 상세조회
    @GetMapping("/list/{id}")
    public ResponseDto<?> readBlog(@PathVariable Long id) {
        return boardService.getOnePost(id);
    }


    // 글수정
    @PutMapping("/auth/list/{id}")
    public ResponseDto<?> modifyBlog(@PathVariable Long id,
                                     @RequestBody BoardRequestDto requestDto) {
        return boardService.modifyPost(id, requestDto);
    }


    //글삭제
    @DeleteMapping("/auth/list/{id}")
    public ResponseDto<?> deleteBlog(@PathVariable Long id,
                                     @RequestBody String password) {
        return boardService.deletePost(id, password);
    }

    //패스워드 확인
    @PostMapping("/auth/list/{id}")
    public ResponseDto<?> checkPassword(@PathVariable Long id,
                                        @RequestBody String password) {
        return boardService.checkPassword(id, password);
    }

}

