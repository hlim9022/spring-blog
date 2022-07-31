package com.sparta.week01.service;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.Comment;
import com.sparta.week01.dto.CommentDto;
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
    public ResponseDto<?> getAllComments(Long boardId) {
        List<Comment> commentList = getCommentList(boardId);
        return ResponseDto.success(commentList);
    }

    // 댓글 작성
    @Transactional
    public ResponseDto<?> addComments(Long boardId, CommentDto commentDto) {

        //boardId 값으로 게시글을 찾는다
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 없습니다.")
        );

        Comment saveComment = commentRepository.save(new Comment(commentDto.getComment(), foundBoard));

        foundBoard.addComment(saveComment);
        System.out.println(foundBoard);

        return ResponseDto.success(saveComment);
    }


    // 댓글 수정
    @Transactional
    public ResponseDto<?> updateComment(Long boardId, Long commentId, CommentDto commentDto) {

        List<Comment> commentList = getCommentList(boardId);

        // 댓글리스트 중에 수정을 원하는 특정 댓글, 댓글리스트에서 찾을 때는 인덱스값을 사용했기 때문에 commentId - 1
        try {
            Comment foundComment = commentList.get(commentId.intValue()-1);

            // 댓글 내용 수정
            foundComment.update(commentDto.getComment());
            return ResponseDto.success(foundComment);
        } catch (IndexOutOfBoundsException e) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }
    }


    // 댓글 삭제
    @Transactional
    public ResponseDto<?> removeComment(Long boardId, Long commentId) {

        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }

        return ResponseDto.success("success");
    }


    // 게시글 id를 통해 댓글리스트를 구하는 method
    private List<Comment> getCommentList(Long boardId) {
        // 게시글 찾기
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 없습니다.")
        );

        // 찾은 게시글의 댓글리스트
        List<Comment> commentList = commentRepository.findAllByBoard(foundBoard);
        return commentList;
    }
}
