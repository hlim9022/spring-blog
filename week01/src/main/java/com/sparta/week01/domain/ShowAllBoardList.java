package com.sparta.week01.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowAllBoardList {
    LocalDateTime getCreatedAt();

    LocalDateTime getModifiedAt();
    Long getId();
    String getTitle();

    String getContent();
    
    User getUser();
}


