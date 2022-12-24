package com.team2.levelog.post.dto;
import lombok.Getter;

@Getter
public class ResponseDto {
    private int statusCode;
    private String message;

    public ResponseDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
