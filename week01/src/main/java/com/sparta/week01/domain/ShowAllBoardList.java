package com.sparta.week01.domain;

import java.time.LocalDate;

public interface ShowAllBoardList {
    Long getId();
    LocalDate getCreatedAt();
    String getAuthor();
    String getTitle();
}


