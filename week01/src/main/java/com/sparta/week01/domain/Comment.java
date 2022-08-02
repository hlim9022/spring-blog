package com.sparta.week01.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Comment(String comment, Board board) {
        this.comment = comment;
        this.board = board;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}

