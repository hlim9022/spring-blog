package com.sparta.week01.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.week01.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BOARD_ID")
    @JsonIgnore
    private Board board;

    public Comment(String comment, Board board) {
        this.comment = comment;
        this.board = board;
    }
}