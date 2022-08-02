package com.sparta.week01.sercurity.filter;

/*
 Client는 이제부터 Header에 JWT 토큰을 넣어서 요청을 줘야하고
 유효성을 확인 후에 서버는 응답을해준다
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sparta.week01.domain.User;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.repository.UserRepository;
import com.sparta.week01.sercurity.UserDetailsImpl;
import com.sparta.week01.sercurity.jwt.JwtProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/*
    API요청 Header에 전달되는 JWT토큰 유효성 검사(인증)
 */
public class JwtAuthFilter extends BasicAuthenticationFilter {


    private final UserRepository userRepository;

    public JwtAuthFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //request Header에 Token값 확인
        String jwtInHeader = request.getHeader(JwtProperties.AUTH_HEADER);
        System.out.println(jwtInHeader);

        if(jwtInHeader == null || !jwtInHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {

            chain.doFilter(request,response);
            return;
        }

        // Bearer 제외한 token정보만 추출
        String jwtToken = jwtInHeader.replace(JwtProperties.TOKEN_PREFIX, "");

        // Algorithm과 secret key를 제공하여 복호화하는 메소드실행
        DecodedJWT decodedJWT = decodeJwt(jwtToken);
        String username = decodedJWT.getClaim("USERNAME").asString();

        Date expireDate = decodedJWT.getClaim("EXPIRATION_TIME").asDate();
        Date now = new Date();
        if(expireDate.before(now)) {

            ResponseDto.fail("Invalid Token", "Token이 유효하지 않습니다.");
        }



        System.out.println("---------- 검증시작 -----------------");

        // 정상적으로 서명이 된 경우
        if(username != null) {
            User userEntity = userRepository.findByUsername(username);
            UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);

            //강제로 authentication을 만들어 로그인을 시켜줘야함
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null);

            // 강제로 security의 session에 접근하여 authentication객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication); // 저장할 수 있는 session공간을 찾음
            System.out.println("---------- 검증완료 -----------------");

            chain.doFilter(request, response);
        }
    }

    private DecodedJWT decodeJwt(String jwtToken) {
        DecodedJWT jwt = null;

        try{
            Algorithm algorithm = Algorithm.HMAC512(JwtProperties.SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();

            jwt = verifier.verify(jwtToken);
        } catch (Exception e) {
            ResponseDto.fail("INVALID TOKEN", "유효한 토큰이 아닙니다.");
        }
        return jwt;


    }
}
