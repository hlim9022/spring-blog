package com.sparta.week01.repository;

import com.sparta.week01.entity.Board;
import com.sparta.week01.entity.ShowAllBoardList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<ShowAllBoardList> findAllByOrderByCreatedAtDesc();
}
