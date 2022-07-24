package com.sparta.week01.controller;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.BoardRepository;
import com.sparta.week01.domain.BoardRequestDto;
import com.sparta.week01.domain.Request;
import com.sparta.week01.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;


@RestController
@AllArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

//    @GetMapping("/blog/list")
//    public ResponseEntity<ResponseHandler> getBlogList(){
//        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
//        ResponseHandler handler = new ResponseHandler(true, boardList, null);
//
//
//        return new ResponseEntity<>(handler, HttpStatus.OK);
//    }

    @GetMapping("/blog/list")
    public List<Board> getBlogList() {
         return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping("/blog/list")
    public Board createBlog(@RequestBody BoardRequestDto requestDto) {
        return boardRepository.save(new Board(requestDto));
    }

    @DeleteMapping("/blog/list/{id}")
    public Long deleteBlog(@PathVariable Long id) {
        boardRepository.deleteById(id);
        return id;
    }

    @PutMapping("/blog/list/{id}")
    public String modifyBlog(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @GetMapping("/blog/list/{id}")
    public Board readBlog(@PathVariable Long id) {


        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("찾으시는 글이 존재하지 않습니다.")
        );

        return board;
    }


}
