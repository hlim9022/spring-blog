package com.sparta.week01.service;

import com.sparta.week01.domain.User;
import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.dto.UserLoginDto;
import com.sparta.week01.dto.UserSignupDto;
import com.sparta.week01.repository.UserRepository;
import com.sparta.week01.sercurity.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> join(UserSignupDto userSignupDto) {
        System.out.println(userSignupDto.getPasswordConfirm());
        System.out.println(userSignupDto.getUsername());

        String username = userSignupDto.getUsername();
        UserLoginDto userLoginDto = new UserLoginDto();

        if(!usernameDupCheck(username)){
            return new ResponseEntity<>(ResponseDto.fail("EXIST NICKNAME", "중복된 닉네임입니다."),HttpStatus.OK);
        }

        if (checkUsername(username)) {
            String password = userSignupDto.getPassword();
            boolean pwLen = checkPassword(password);
            String passwordConfirm = userSignupDto.getPasswordConfirm();

            if(pwLen && password.equals(passwordConfirm)) {
                userLoginDto.setPassword(passwordEncoder.encode(password));
                userLoginDto.setUsername(userSignupDto.getUsername());
                User saveUser = userRepository.save(new User(userLoginDto));
                return new ResponseEntity<>(ResponseDto.success(saveUser), HttpStatus.OK);
            } else if(!password.equals(passwordConfirm)){
                return new ResponseEntity<>(ResponseDto.fail("PASSWORD MISMATCH",
                        "비밀번호와 비밀번호확인이 일치하지 않습니다."),HttpStatus.OK);
            } else if(!pwLen) {
                return new ResponseEntity<>(ResponseDto.fail("PASSWORD WRONG FORMAT",
                        "4-32자의 영문 소문자, 숫자만 사용 가능합니다."),HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(ResponseDto.fail("NICKNAME WRONG FORMAT",
                "4-12자의 영문 대문자,소문자, 숫자만 사용 가능합니다."),HttpStatus.OK);
    }

    private boolean usernameDupCheck(String username) {
        User foundUser = userRepository.findByUsername(username);
        return foundUser == null;
    }

    // password validation
    private boolean checkPassword(String password) {
        char[] chList = password.toCharArray();
        if(chList.length < 4 || chList.length > 32) {
            return false;
        }

        for(char ch : chList) {
            if(!((ch >= 'a' && ch <= 'z') ||
                    (ch >= '0' && ch <= '9'))) {
                return false;
            }
        }
        return true;
    }

    // username validation
    private boolean checkUsername(String username) {
        /*
        닉네임 체크
        - 최소 4자이상, 최대 12자 이하
        - 알파벳 대소문자, 숫자로만 구성
         */
        char[] chList = username.toCharArray();
        if(chList.length < 4 || chList.length > 12) {
            return false;
        }

        for(char ch : chList) {
            if(!((ch >= 'a' && ch <= 'z') ||
                    (ch >= 'A' && ch <= 'Z') ||
                    (ch >= '0' && ch <= '9'))) {
                return false;
            }
        }
        return true;
    }


    public User getUserInfo(UserDetailsImpl userDetails) {
        return userRepository.findByUsername(userDetails.getUsername());
    }
}
