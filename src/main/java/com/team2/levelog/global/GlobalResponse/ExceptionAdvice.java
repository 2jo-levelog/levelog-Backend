package com.team2.levelog.global.GlobalResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.team2.levelog.global.GlobalResponse.code.ErrorCode.INVALID_ID_PASSWORD;

// 1. 기능 : 전역에서 발생하는 예외를 핸들링
// 2. 작성자 : 조소영
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    // 커스텀한 실행 예외
    @ExceptionHandler(value= {CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException e){
        log.error("====================== handleCustomException에서 처리한 에러 : {}", e.getMessage());
        return ResponseUtil.errorResponse(e.getErrorCode());
    }

    // 정규식 예외
    @ExceptionHandler(value= {MethodArgumentNotValidException.class})
    protected ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
        log.error("====================== handleValidationException에서 처리한 에러 : {}", e.getMessage());
        return ResponseUtil.errorResponse(INVALID_ID_PASSWORD);
    }

    // 데이터 예외



}
