package com.sparta.week01.domain;

import java.time.LocalDateTime;

public interface ShowAllBoardList {
    Long getId();
    LocalDateTime getCreatedAt();
    String getAuthor();
    String getTitle();
}


