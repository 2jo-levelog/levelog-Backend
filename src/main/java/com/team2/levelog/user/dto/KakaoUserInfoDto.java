package com.team2.levelog.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 1. 기능    : 카카오로부터 유저정보 받아오는 Dto
// 2. 작성자  : 조소영
@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String email;
    private String nickname;
    private String thumbImg;

    public KakaoUserInfoDto(Long id, String nickname, String email, String thumbImg) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.thumbImg = thumbImg;
    }

}