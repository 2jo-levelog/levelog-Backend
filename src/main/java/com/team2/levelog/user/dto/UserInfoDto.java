package com.team2.levelog.user.dto;

import lombok.Getter;

@Getter
public class UserInfoDto {
    private String email;
    private String nickName;
    private String profileImg;

    public UserInfoDto(String email, String nickName, String profileImg){
        this.email = email;
        this.nickName = nickName;
        this.profileImg = profileImg;
    }
}
