package com.sparta.week01.controller;

import com.sparta.week01.dto.ResponseDto;
import com.sparta.week01.dto.UserSignupDto;
import com.sparta.week01.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/members")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody UserSignupDto userSignupDto) {
        System.out.println("가입시작");
        return userService.join(userSignupDto);
    }

}
