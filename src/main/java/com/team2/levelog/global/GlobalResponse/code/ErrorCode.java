package com.team2.levelog.global.GlobalResponse.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 1. 기능 : 예외 메세지 커스텀
// 2. 작성자 : 조소영
@Getter
@AllArgsConstructor
public enum ErrorCode {

    //400 BAD_REQUEST : 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청이 올바르지 않습니다"),
    BAD_REQUEST_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일이 존재합니다."),
    EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임이 존재합니다."),
    NOTEXIST_EMAIL(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다."),
    LOGIN_MATCH_FAIL(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),
    INVALID_ID_PASSWORD(HttpStatus.BAD_REQUEST, "아이디나 비밀번호의 구성이 알맞지 않습니다"),
    BAD_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),


    // 401 UNAUTHORIZED : 인증되지 않은 사용자
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    NO_ACCESS(HttpStatus.UNAUTHORIZED, "작성자만 삭제/수정할 수 있습니다."),


    // 404 NOT_FOUND : Resource 를 찾을 수 없음
    NOT_FOUND(HttpStatus.BAD_REQUEST, "NOT_FOUND"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."),

    //409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),

    //500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    MALFORMED_JWT(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않는 JWT 입니다"),
    EXPIRED_JWT(HttpStatus.INTERNAL_SERVER_ERROR, "만료된 JWT 입니다"),
    EXUnsupportedJwt_JWT(HttpStatus.INTERNAL_SERVER_ERROR, "지원되지 않는 JWT 입니다"),
    SPRING_SECURITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "시큐리티 에러");

    private final HttpStatus httpStatus;
    private final String message;
}
