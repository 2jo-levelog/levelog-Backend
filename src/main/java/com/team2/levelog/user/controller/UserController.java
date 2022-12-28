package com.team2.levelog.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team2.levelog.global.GlobalResponse.GlobalResponseDto;
import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.security.UserDetailsImpl;
import com.team2.levelog.user.dto.DupRequestCheck;
import com.team2.levelog.user.dto.SigninRequestDto;
import com.team2.levelog.user.dto.SignUpRequestDto;
import com.team2.levelog.user.service.KakaoService;
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
    private final KakaoService kakaoService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ResponseUtil.successResponse(SuccessCode.SIGNUP_OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response) {
        userService.login(signinRequestDto, response);
        return ResponseUtil.successResponse(SuccessCode.SIGNIN_OK);
    }

    @PostMapping("/dupEmail")
    public ResponseEntity<?> dupEmailCheck(@RequestBody DupRequestCheck requestCheck) {
        if(userService.dupCheckEmail(requestCheck)){
            return ResponseUtil.errorResponse(ErrorCode.EXIST_EMAIL);
        }
        return ResponseUtil.successResponse(SuccessCode.AVAILABLE_EMAIL);
    }

    @PostMapping("/dupNick")
    public ResponseEntity<?> dupNickCheck(@RequestBody DupRequestCheck requestCheck) {
        if(userService.dupCheckNick(requestCheck)){
            return ResponseUtil.errorResponse(ErrorCode.EXIST_NICKNAME);
        }
        return ResponseUtil.successResponse(SuccessCode.AVAILABLE_NICKNAME);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseUtil.successResponse(userService.getUserInfo(userDetails.getUser()));
    }

    // 카카오 로그인
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        // 인가코드를 서비스로 전달
        kakaoService.kakaoLogin(code, response);
        return ResponseEntity.ok().body(new GlobalResponseDto(SuccessCode.SIGNIN_OK));
    }

}
