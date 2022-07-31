package com.sparta.week01.service;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.Comment;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.repository.BoardRepository;
import com.sparta.week01.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 댓글 전체 조회(권한 필요X)
    @Transactional
    public List<Comment> getAllComments(Long boardId) {
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 없습니다.")
        );

        return commentRepository.findAllByBoard(foundBoard);
    }

    @Transactional
    public ResponseDto<?> addComments(Long boardId, String comment) {

        //boardId 값으로 게시글을 찾는다
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 없습니다.")
        );

        // JSON 형태로 입력된 comment를 String으로 추출
        JSONObject jsonObject = new JSONObject(comment);
        String newComment = jsonObject.getString("comment");

        Comment saveComment = commentRepository.save(new Comment(newComment, foundBoard));

        foundBoard.addComment(saveComment);
        System.out.println(foundBoard);

        return null;

    }
}
