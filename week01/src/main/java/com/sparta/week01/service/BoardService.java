package com.sparta.week01.service;

import com.sparta.week01.domain.*;
import com.sparta.week01.dto.BoardRequestDto;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    //블로그 전체 리스트 조회
    @Transactional
    public ResponseDto<?> getAllBlogList() {
        List<ShowAllBoardList> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        return ResponseDto.success(boardList);
    }

    //블로그 글작성
    @Transactional
    public ResponseDto<?> createBlog(BoardRequestDto requestDto) {
        Board addBoard = boardRepository.save(new Board(requestDto));
        return ResponseDto.success(addBoard);
    }


    @Transactional
    public Board update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("찾으시는 id가 없습니다."));
        board.update(requestDto);
        return board;
    }

    @Transactional
    public ResponseDto<?> modifyPost(Long id, BoardRequestDto requestDto) {
        if(boardRepository.findById(id).isPresent()) {
            Board update = update(id, requestDto);
            return ResponseDto.success(update);
        } else {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
    }


    @Transactional
    public ResponseDto<?> getOnePost(Long id) {

        if(boardRepository.findById(id).isPresent()) {
            Board findOne = boardRepository.findById(id).get();
            return ResponseDto.success(findOne);
        } else {
            return ResponseDto.fail("WRONG_PASSWORD", "password is wrong.");
        }
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id) {
        if(boardRepository.findById(id).isPresent()) {
            boardRepository.deleteById(id);
            return ResponseDto.success("delete success");
        } else {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
    }
}
