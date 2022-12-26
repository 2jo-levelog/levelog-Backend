package com.team2.levelog.global.GlobalResponse;

import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 1. 기능 : 실행 예외에 ErrorCode필드 추가해 커스텀
// 2. 작성자 : 조소영
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {     //실행 예외 클래스를 상속받아서 Unchecked Exception으로 활용
    private final ErrorCode errorCode;
}
