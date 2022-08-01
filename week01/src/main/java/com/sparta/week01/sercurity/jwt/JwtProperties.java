package com.sparta.week01.sercurity.jwt;

public interface JwtProperties {
    String SECRET_KEY = "kelly"; // 우리서버만 알고 있는 비밀값
    String TOKEN_PREFIX = "BEARER ";
    String AUTH_HEADER = "Authorization";

    int ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30; // 30분
}
