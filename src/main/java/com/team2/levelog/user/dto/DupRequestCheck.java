package com.team2.levelog.user.dto;

import lombok.Getter;

// 1. 기능   : 회원가입시 중복 체크 입력값
// 2. 작성자 : 서혁수
@Getter
public class DupRequestCheck {
    private String email;
    private String nickname;
}
