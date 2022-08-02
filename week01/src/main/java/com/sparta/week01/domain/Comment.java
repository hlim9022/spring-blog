package com.sparta.week01.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.week01.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "BOARD_ID")
    @JsonIgnore
    private Board board;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private User user;

    public Comment(CommentDto commentDto, Board board, User user) {
        this.comment = commentDto.getComment();
        this.username = commentDto.getUser().getUsername();
        this.board = board;
        this.user = user;
    }

    public void update(CommentDto commentDto, Board board, User user) {
        this.comment = commentDto.getComment();
        this.board = board;
        this.user = user;
    }
}

