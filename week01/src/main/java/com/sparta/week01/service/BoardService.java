package com.sparta.week01.service;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.BoardRepository;
import com.sparta.week01.domain.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public String update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("찾으시는 글이 없습니다.")
        );

        if(board.getPassword() == requestDto.getPassword()){
            board.update(requestDto);
            return "변경 완료!";
        } else {
            return "잘못된 비밀번호입니다.";
        }
    }

}
