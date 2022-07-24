package com.sparta.week01.controller;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.BoardRepository;
import com.sparta.week01.domain.BoardRequestDto;
import com.sparta.week01.domain.ResponseTemp;
import com.sparta.week01.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@AllArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping("/blog/list")
    public ResponseEntity<ResponseTemp<List<Board>>> getBlogList() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        ResponseTemp<List<Board>> res = new ResponseTemp<>();
        return new ResponseEntity<>(res.getHttpResponseTemp(boardList), HttpStatus.OK);
    }

    @PostMapping("/blog/list")
    public ResponseEntity<ResponseTemp<Board>> createBlog(@RequestBody BoardRequestDto requestDto) {
        Board addBoard = boardRepository.save(new Board(requestDto));
        ResponseTemp<Board> res = new ResponseTemp<>();
        return new ResponseEntity<>(res.getHttpResponseTemp(addBoard), HttpStatus.OK);
    }

    @DeleteMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> deleteBlog(@PathVariable Long id) {
        ResponseTemp<Board> res = new ResponseTemp<>();
            boardRepository.deleteById(id);

            res.setSuccess(HttpStatus.ACCEPTED.is2xxSuccessful());
            res.setData(null);
            res.setError(res.getError());

        return new ResponseEntity<>(res,HttpStatus.OK);
    }


    @PutMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> modifyBlog(@PathVariable Long id,
                                                          @RequestBody BoardRequestDto requestDto) {

        Board update = boardService.update(id, requestDto);
        ResponseTemp<Board> res = new ResponseTemp<>();
        return new ResponseEntity<>(res.getHttpResponseTemp(update), HttpStatus.OK);
    }



    @PostMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Boolean>>  checkPassword(@PathVariable Long id, Integer password) {
        boardService.checkPassword(id,password);

        ResponseTemp<Boolean> res = new ResponseTemp<>();
        return null;
    }


    @GetMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> readBlog(@PathVariable Long id) {
        Board findOne = boardRepository.findById(id).get();
        ResponseTemp<Board> res = new ResponseTemp<>();
        return new ResponseEntity<>(res.getHttpResponseTemp(findOne), HttpStatus.OK);
    }

}

