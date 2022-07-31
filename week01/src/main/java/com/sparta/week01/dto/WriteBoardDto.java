package com.sparta.week01.dto;

import javax.persistence.ManyToOne;

public class WriteBoardDto {

    @ManyToOne
    private Long user_id;

    @ManyToOne
    private Long board_id;

}
