package com.team2.levelog.user.dto;


import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import lombok.Getter;

// 1. 기능   : 카카오톡 반환 Dto
// 2. 작성자 : 조소영
@Getter
public class KakaoResponseDto {
    private int status;
    private String msg;

    public KakaoResponseDto(SuccessCode successCode){
        this.status = successCode.getHttpStatus().value();
        this.msg = successCode.getMessage();

    }
}