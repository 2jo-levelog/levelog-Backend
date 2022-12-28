package com.team2.levelog.user.controller;

import com.team2.levelog.global.Email.EmailService;
import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.security.UserDetailsImpl;
import com.team2.levelog.user.dto.DupRequestCheck;
import com.team2.levelog.user.dto.SigninRequestDto;
import com.team2.levelog.user.dto.SignUpRequestDto;
import com.team2.levelog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

// 1. 기능   : 회원 관련 종합 컨트롤러
// 2. 작성자 : 서혁수
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;


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

    // 회원 임시 가입 및 확인 메일 전송
    @PostMapping("/email/signUp")
    public ResponseEntity<?> emailSignUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) throws Exception {

        userService.emailSignUp(signUpRequestDto);

        return ResponseUtil.successResponse(SuccessCode.SEND_EMAIL);
    }

    // 이메일 확인 : 전송된 email에서 접근하는 url로 최종 가입 확인 절차
    @GetMapping("/email/confirm/{emailConfirmCode}")
    public ResponseEntity<?> emailConfirm(@PathVariable String emailConfirmCode){
        userService.emailConfirm(emailConfirmCode);
        return ResponseUtil.successResponse(SuccessCode.REGISTER_OK);
    }



}
