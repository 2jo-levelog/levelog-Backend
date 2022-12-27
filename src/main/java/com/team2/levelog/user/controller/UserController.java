package com.team2.levelog.user.controller;

import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.security.UserDetailsImpl;
import com.team2.levelog.user.dto.SigninRequestDto;
import com.team2.levelog.user.dto.SignUpRequestDto;
import com.team2.levelog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

// 1. 기능   : 회원 관련 종합 컨트롤러
// 2. 작성자 : 서혁수
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ResponseUtil.successResponse(SuccessCode.SIGNUP_OK);
    }

    // 로그인
    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response) {
        userService.login(signinRequestDto, response);
        return ResponseUtil.successResponse(SuccessCode.SIGNIN_OK);
    }

    // 토큰 재발행
    @GetMapping("/issue/token")
    public ResponseEntity<?> issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return userService.issuedToken(userDetails.getUser().getEmail(), userDetails.getUser().getNickname(), response);
    }

    // 로그아웃
    @PostMapping("/signOut")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.signOut(userDetails.getUser().getNickname());
    }


//    @GetMapping("/issue/token")
//    public ResponseEntity<?> issueToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
//        return userService.
//    }

//    @PostMapping("/dupEmail")
//    public ResponseEntity<?> dupEmailCheck(@RequestBody DupRequestCheck requestCheck) {
//        if(userService.dupCheckEmail(requestCheck)){
//            return ResponseUtil.errorResponse(ErrorCode.EXIST_EMAIL);
//        }
//        return ResponseUtil.successResponse(SuccessCode.AVAILABLE_EMAIL);
//    }
//
//    @PostMapping("/dupNick")
//    public ResponseEntity<?> dupNickCheck(@RequestBody DupRequestCheck requestCheck) {
//        if(userService.dupCheckNick(requestCheck)){
//            return ResponseUtil.errorResponse(ErrorCode.EXIST_NICKNAME);
//        }
//        return ResponseUtil.successResponse(SuccessCode.AVAILABLE_NICKNAME);
//    }

}
