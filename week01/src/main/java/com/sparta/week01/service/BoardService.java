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
    public Board update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("찾으시는 글이 없습니다.")
        );

        board.update(requestDto);
        return board;
    }

    @Transactional
    public boolean checkPassword(Long id, Integer password) {
//        boardRepository.findById(id).stream()
        return boardRepository.findById(id).filter(x->x.getPassword().equals(password))
                .isPresent();
    }

}
