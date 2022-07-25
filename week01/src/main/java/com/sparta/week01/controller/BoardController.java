package com.sparta.week01.controller;

import com.sparta.week01.domain.*;
import com.sparta.week01.service.BoardService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardRepository boardRepository, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.boardService = boardService;
    }

    //전체 블로그글 목록 조회
    @GetMapping("/blog/list")
    public ResponseEntity<ResponseTemp<List<ShowAllBoardList>>> getBlogList() {
        List<ShowAllBoardList> boardList = boardRepository.findAllByOrderByCreatedAtDesc();

        ResponseTemp<List<ShowAllBoardList>> res = new ResponseTemp<>();
        return new ResponseEntity<>(res.getHttpResponseTemp(HttpStatus.OK.value(), boardList), HttpStatus.OK);
    }


    //글작성
    @PostMapping("/blog/list")
    public ResponseEntity<ResponseTemp<Board>> createBlog(@RequestBody BoardRequestDto requestDto) {
        Board addBoard = boardRepository.save(new Board(requestDto));
        ResponseTemp<Board> res = new ResponseTemp<>();
        return new ResponseEntity<>(res.getHttpResponseTemp(HttpStatus.OK.value(), addBoard), HttpStatus.OK);
    }

    //글 상세조회
    @GetMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> readBlog(@PathVariable Long id) {
        ResponseTemp<Board> res = new ResponseTemp<>();

        if(boardRepository.findById(id).isPresent()) {
            Board findOne = boardRepository.findById(id).get();
            return new ResponseEntity<>(res.getHttpResponseTemp(HttpStatus.OK.value(), findOne), HttpStatus.OK);
        } else {

            res.getHttpResponseTemp(HttpStatus.NOT_FOUND.value(),null);
            return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
        }
    }


    //글삭제
    @DeleteMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> deleteBlog(@PathVariable Long id,
                                                          @RequestBody String password) {
        ResponseTemp<Board> res = new ResponseTemp<>();

        JSONObject jsonObject = new JSONObject(password);
        int convertPw = jsonObject.getInt("password");

        if(boardRepository.findById(id).isPresent()) {
            if (boardService.checkPassword(id, convertPw)) {
                boardRepository.deleteById(id);
                res.getHttpResponseTemp(HttpStatus.OK.value(), null);

                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                res.getHttpResponseTemp(HttpStatus.BAD_REQUEST.value(), null);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        } else {
            res.getHttpResponseTemp(HttpStatus.NOT_FOUND.value(), null);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

    }


    //글수정
    @PutMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> modifyBlog(@PathVariable Long id,
                                                          @RequestBody BoardRequestDto requestDto) {
        ResponseTemp<Board> res = new ResponseTemp<>();

        if(boardRepository.findById(id).isPresent()) {
            if(boardService.checkPassword(id,requestDto.getPassword())){
                Board update = boardService.update(id, requestDto);
                return new ResponseEntity<>(res.getHttpResponseTemp(HttpStatus.OK.value(), update), HttpStatus.OK);
            } else {
                res.getHttpResponseTemp(HttpStatus.BAD_REQUEST.value(),null);
                return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
            }
        } else {
            res.getHttpResponseTemp(HttpStatus.NOT_FOUND.value(),null);
            return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
        }
    }



    //패스워드 확인
    @PostMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Boolean>>  checkPassword(@PathVariable Long id, @RequestBody String password) {
        ResponseTemp<Boolean> res = new ResponseTemp<>();

        JSONObject jsonObject = new JSONObject(password);
        int convertPw = jsonObject.getInt("password");

        boolean result = boardService.checkPassword(id, convertPw);

        if(result) {
            return new ResponseEntity<>(res.getHttpResponseTemp(HttpStatus.OK.value(),true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res.getHttpResponseTemp(HttpStatus.BAD_REQUEST.value(), false), HttpStatus.NOT_FOUND);
        }
    }




}

