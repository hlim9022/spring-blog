package com.sparta.week01.sercurity;

import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.repository.UserRepository;
import com.sparta.week01.sercurity.jwt.JwtTokenUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Login 성공시에 호출되는 클래스
    -> 로그인이 되었으니 JWT Token 생성해주는 역할
 */
@NoArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String TOKEN_PREFIX = "BEARER ";
    private static final String AUTH_HEADER = "Authorization";

    private UserRepository userRepository;

    @Autowired
    public LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        // authentication.getPrincipal() 실행하면 UserDetails를 구현한 사용자 객체를 반환
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //Token 생성
        final String accessJwtToken = JwtTokenUtils.generateACJwtToken(userDetails);
        System.out.println("access 토큰생성 및 로그인 완료");




        response.addHeader(AUTH_HEADER, TOKEN_PREFIX + accessJwtToken);

    }
}
