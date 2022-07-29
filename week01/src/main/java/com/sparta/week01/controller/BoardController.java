package com.sparta.week01.controller;

import com.sparta.week01.domain.BoardRequestDto;
import com.sparta.week01.domain.ResponseTemp;
import com.sparta.week01.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;


    //전체 블로그글 목록 조회
    @GetMapping("/blog/list")
    public ResponseTemp<?> getBlogList() {
        return boardService.getAllBlogList();
    }

    //글작성
    @PostMapping("/blog/list")
    public ResponseTemp<?> createBlog(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBlog(requestDto);
    }

    //글 상세조회
    @GetMapping("/blog/list/{id}")
    public ResponseTemp<?> readBlog(@PathVariable Long id) {
        return boardService.getOnePost(id);
    }


    // 글수정
    @PutMapping("/blog/list/{id}")
    public ResponseTemp<?> modifyBlog(@PathVariable Long id,
                                      @RequestBody BoardRequestDto requestDto) {
        return boardService.modifyPost(id, requestDto);
    }


    //글삭제
    @DeleteMapping("/blog/list/{id}")
    public ResponseTemp<?> deleteBlog(@PathVariable Long id,
                                        @RequestBody String password) {
        return boardService.deletePost(id, password);
    }

    //패스워드 확인
    @PostMapping("/blog/list/{id}")
    public ResponseTemp<?>  checkPassword(@PathVariable Long id,
                                          @RequestBody String password) {
        return boardService.checkPassword(id, password);
    }

}

