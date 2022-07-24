package com.sparta.week01.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String author;

    @JsonIgnore
    @Column(nullable = false)
    private Integer password;


    public Board(String author, String title, String contents, int password) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.password = password;
    }


    public Board(BoardRequestDto requestDto){
        this.author = requestDto.getAuthor();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public void update(BoardRequestDto requestDto) {
        this.author = requestDto.getAuthor();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
