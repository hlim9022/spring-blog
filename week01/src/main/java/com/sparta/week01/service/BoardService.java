package com.sparta.week01.service;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.BoardRepository;
import com.sparta.week01.domain.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    @Transactional
    public Board update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).get();
        board.update(requestDto);
        return board;
    }

    @Transactional
    public boolean checkPassword(Long id, Integer password) {
        Board foundBoard = boardRepository.findById(id).get();
        return Objects.equals(foundBoard.getPassword(), password);
    }

}
