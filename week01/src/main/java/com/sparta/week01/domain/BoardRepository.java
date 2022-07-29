package com.sparta.week01.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<ShowAllBoardList> findAllByOrderByCreatedAtDesc();
}
