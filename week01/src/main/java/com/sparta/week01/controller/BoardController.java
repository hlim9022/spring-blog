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

import java.util.HashMap;
import java.util.List;


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
        if(boardRepository.findById(id).isPresent()){
            boardRepository.deleteById(id);
            res.getHttpResponseTemp(null);

            return new ResponseEntity<>(res,HttpStatus.OK);
        } else {
            res.setSuccess(false);
            res.setData(null);
            res.setError(new HashMap<>(){{ put("code","NULL_POST_ID");
                put("message","post id isn't exist");
            }});

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> modifyBlog(@PathVariable Long id,
                                                          @RequestBody BoardRequestDto requestDto) {

        ResponseTemp<Board> res = new ResponseTemp<>();
        if(boardRepository.findById(id).isPresent()){
            Board update = boardService.update(id, requestDto);
            return new ResponseEntity<>(res.getHttpResponseTemp(update), HttpStatus.OK);
        } else {
            res.setSuccess(false);
            res.setData(null);
            res.setError(new HashMap<>(){{ put("code","NULL_POST_ID");
                put("message","post id isn't exist");
            }});

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Boolean>>  checkPassword(@PathVariable Long id, Integer password) {
        boardService.checkPassword(id,password);

        ResponseTemp<Boolean> res = new ResponseTemp<>();
        return null;
    }


    @GetMapping("/blog/list/{id}")
    public ResponseEntity<ResponseTemp<Board>> readBlog(@PathVariable Long id) {
        ResponseTemp<Board> res = new ResponseTemp<>();

        if(boardRepository.findById(id).isPresent()) {
            Board findOne = boardRepository.findById(id).get();

            return new ResponseEntity<>(res.getHttpResponseTemp(findOne), HttpStatus.OK);
        } else {
            res.setSuccess(false);
            res.setData(null);
            res.setError(new HashMap<>(){{ put("code","NULL_POST_ID");
                put("message","post id isn't exist");
            }});

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

}

