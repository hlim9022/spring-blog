package com.sparta.week01.sercurity.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sparta.week01.sercurity.UserDetailsImpl;

import java.util.Date;


public class JwtTokenUtils {

    private static final int ACCESS_TOKEN_EXPIRE_TIME = 1000 * 30; // 30분
    private static final int REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60;
            //* 60 * 24 * 7; // 1주일


    public static String generateACJwtToken(UserDetailsImpl userDetails) {
        return JWT.create()
                .withIssuer("kelly")
                .withClaim("EXPIRATION_TIME", new Date(System.currentTimeMillis()+ACCESS_TOKEN_EXPIRE_TIME))
                .withClaim("USERNAME", userDetails.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));

    }

    public static String generateREJwtToken(UserDetailsImpl userDetails) {

        return JWT.create()
                .withIssuer("kelly")
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
    }
}
