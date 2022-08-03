package com.sparta.week01.sercurity.jwt;

public interface JwtProperties {
    String SECRET_KEY = "kelly"; // 우리서버만 알고 있는 비밀값
    String TOKEN_PREFIX = "BEARER ";
    String AUTH_HEADER = "Authorization";
    String REFRESH_HEADER = "Refresh-Token";
}
