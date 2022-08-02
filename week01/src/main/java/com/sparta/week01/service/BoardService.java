package com.sparta.week01.service;

import com.sparta.week01.domain.Board;
import com.sparta.week01.domain.ShowAllBoardList;
import com.sparta.week01.domain.User;
import com.sparta.week01.dto.BoardRequestDto;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.repository.BoardRepository;
import com.sparta.week01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    //블로그 전체 리스트 조회
    @Transactional
    public ResponseDto<?> getAllBlogList() {
        List<ShowAllBoardList> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        return ResponseDto.success(boardList);
    }

    //블로그 글작성
    @Transactional
    public ResponseDto<?> createBlog(BoardRequestDto requestDto, String username) {
        User foundUser = userRepository.findByUsername(username);
        requestDto.setUser(foundUser);
        Board addBoard = boardRepository.save(new Board(requestDto, foundUser));
        return ResponseDto.success(addBoard);
    }


    @Transactional
    public ResponseDto<?> modifyPost(Long id, BoardRequestDto requestDto, String username) {
        User foundUser = userRepository.findByUsername(username);
        Optional<Board> checkBoard = boardRepository.findById(id);


        if(checkBoard.isPresent()) {
            if (Objects.equals(foundUser.getId(), checkBoard.get().getUser().getId())) {
                Board board = checkBoard.get();

                board.update(requestDto, foundUser);
                return ResponseDto.success(board);
            } else {
                return ResponseDto.fail("UNAUTHORIZED", "작성자만 수정할 수 있습니다.");
            }
        }
        return ResponseDto.fail("NULL_POST_ID", "해당 게시글은 존재하지 않는 게시글입니다.");
    }


    @Transactional
    public ResponseDto<?> getOnePost(Long id) {
        Optional<Board> foundBoard = boardRepository.findById(id);

        if(foundBoard.isPresent()) {
            return ResponseDto.success(foundBoard);
        } else {
            return ResponseDto.fail("NULL_POST_ID", "해당 게시글은 존재하지 않는 게시글입니다.");
        }
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, String username) {
        User foundUser = userRepository.findByUsername(username);
        Optional<Board> foundBoard = boardRepository.findById(id);

        if (foundBoard.isPresent()) {
            if (Objects.equals(foundUser.getId(), foundBoard.get().getUser().getId())) {
                boardRepository.deleteById(id);
                return ResponseDto.success("delete success");
            } else {
                return ResponseDto.fail("UNAUTHORIZED", "작성자만 삭제할 수 있습니다.");
            }
        }
        return ResponseDto.fail("NULL_POST_ID", "해당 게시글은 존재하지 않는 게시글입니다.");
    }
}
