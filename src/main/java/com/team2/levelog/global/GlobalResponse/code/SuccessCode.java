package com.team2.levelog.global.GlobalResponse.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    OK(HttpStatus.OK, "OK"),
    SIGNUP_OK(HttpStatus.OK, "회원가입에 성공했습니다"),
    SIGNIN_OK(HttpStatus.OK, "로그인에 성공했습니다"),
    GET_OK(HttpStatus.OK, "조회 성공했습니다"),
    CREATE_OK(HttpStatus.OK, "생성 성공했습니다"),
    MODIFY_OK(HttpStatus.OK, "수정 성공했습니다"),
    DELETE_OK(HttpStatus.OK, "삭제 성공했습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
