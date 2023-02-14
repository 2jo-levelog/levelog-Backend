package com.team2.levelog.global.GlobalResponse.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 1. 기능 : 성공 메세지 커스텀
// 2. 작성자 : 조소영
@Getter
@AllArgsConstructor
public enum SuccessCode {
    OK(HttpStatus.OK, "OK"),
    SIGNUP_OK(HttpStatus.OK, "회원가입에 성공했습니다."),
    SIGNIN_OK(HttpStatus.OK, "로그인에 성공했습니다."),
    GET_OK(HttpStatus.OK, "조회 성공했습니다."),
    CREATE_OK(HttpStatus.OK, "생성 성공했습니다."),
    MODIFY_OK(HttpStatus.OK, "수정 성공했습니다."),
    DELETE_OK(HttpStatus.OK, "삭제 성공했습니다."),
    LIKE_CHECK(HttpStatus.OK, "좋아요 성공했습니다."),
    AVAILABLE_EMAIL(HttpStatus.OK, "사용 가능한 이메일 입니다."),
    AVAILABLE_NICKNAME(HttpStatus.OK, "사용 가능한 닉네임 입니다."),
    SEND_EMAIL(HttpStatus.OK, "인증 메일이 발송되었습니다."),
    REGISTER_OK(HttpStatus.OK, "가입 완료 되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
