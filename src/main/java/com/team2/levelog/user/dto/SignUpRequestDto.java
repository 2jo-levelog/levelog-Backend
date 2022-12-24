package com.team2.levelog.user.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

// 1. 기능   : 회원가입 시 입력 요소 ( 정규식 검사 )
// 2. 작성자 : 서혁수
@Getter
public class SignUpRequestDto {

    // 이메일 정규식 검사
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
            message = "이메일 양식에 맞게 입력해 주세요.")
    private String email;

    // 비밀번호 정규식 검사
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,15}",
            message = "최소 8 자 및 최대 15 자, 대문자 하나 이상, 소문자 하나, 숫자 하나 및 특수 문자 하나 이상")
    private String password;

    // 닉네임 정규식 검사
    @Pattern(regexp = "[a-zA-Z0-9가-힣]{2,12}",
            message = "최소 2 자 최대 12 자, 영어 대소문자, 숫자, 한글 만 사용")
    private String nickname;

    // 프로필 이미지 default 로 들어가는 이미지도 존재한다.
    private String profileImg;
}
