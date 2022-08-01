package com.sparta.week01.repository;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.Comment;
import com.sparta.week01.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard(Board board);
    List<Comment> findAllByUser(User user);
}
