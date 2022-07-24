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
        res.setSuccess(HttpStatus.ACCEPTED.is2xxSuccessful());
        res.setData(boardList);
        res.setError(res.getError());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/blog/list")
    public ResponseEntity<ResponseTemp<Board>> createBlog(@RequestBody BoardRequestDto requestDto) {
        Board addBoard = boardRepository.save(new Board(requestDto));
        ResponseTemp<Board> res = new ResponseTemp<>();
        res.setSuccess(HttpStatus.ACCEPTED.is2xxSuccessful());
        res.setData(addBoard);
        res.setError(res.getError());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> deleteBlog(@PathVariable Long id) {
        ResponseTemp<Board> res = new ResponseTemp<>();
        Board findOne = boardRepository.findById(id).get();
        boardRepository.deleteById(id);

        res.setSuccess(HttpStatus.ACCEPTED.is2xxSuccessful());
        res.setData(findOne);
        res.setError(res.getError());


        return new ResponseEntity<>(res,HttpStatus.OK);
    }

//    @PutMapping("/blog/list/{id}")
//    public String modifyBlog(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
//        return boardService.update(id, requestDto);
//    }
//
//    @GetMapping("/blog/list/{id}")
//    public ResponseEntity<ResponseTemp<Board>> readBlog(@PathVariable Long id) {
//        ResponseTemp<Board> res = new ResponseTemp<>();
//
//        res.setSuccess(HttpStatus.ACCEPTED.is2xxSuccessful());
//        res.setData(addBoard);
//        res.setError(null);
//
//        return board;
//    }


}
