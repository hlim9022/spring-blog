package com.sparta.week01.domain;

import java.time.LocalDateTime;

public interface ShowAllBoardList {
    LocalDateTime getCreatedAt();
    LocalDateTime getModifiedAt();
    Long getId();
    String getTitle();
    String getContent();
    String getUsername();
}


