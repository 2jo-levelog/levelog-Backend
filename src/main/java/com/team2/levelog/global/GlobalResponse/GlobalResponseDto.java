package com.team2.levelog.global.GlobalResponse;

import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import lombok.Getter;

// 1. 기능 : 응답으로 메세지가 필요할 경우를 위한 Dto
// 2. 작성자 : 조소영
@Getter
public class GlobalResponseDto {
    private int statusCode;
    private String message;

    public GlobalResponseDto(SuccessCode successCode){
        this.statusCode = successCode.getHttpStatus().value();
        this.message = successCode.getMessage();
    }

    public GlobalResponseDto(ErrorCode errorCode){
        this.statusCode = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }
}
