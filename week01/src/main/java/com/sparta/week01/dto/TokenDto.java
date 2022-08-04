package com.sparta.week01.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
