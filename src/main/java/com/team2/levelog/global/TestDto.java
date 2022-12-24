package com.team2.levelog.global;

import lombok.Getter;

@Getter
public class TestDto {
    private int status;
    private String msg;

    public TestDto(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
