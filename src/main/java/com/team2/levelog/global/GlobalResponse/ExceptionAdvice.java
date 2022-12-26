package com.team2.levelog.global.GlobalResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import static com.team2.levelog.global.GlobalResponse.code.ErrorCode.*;

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
    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    protected  ResponseEntity<?> handleSQLConstraintException(SQLIntegrityConstraintViolationException e){
        log.error("====================== handleSQLConstraintException에서 처리한 에러 : {}", e.getMessage());
        return ResponseUtil.errorResponse(SQL_CONSTRAINT_VIOLATION);
    }

    // 시큐리티 예외
    @ExceptionHandler(value = {SecurityException.class})
    protected  ResponseEntity<?> handleSecurityException(SecurityException e){
        log.error("====================== handleSecurityException에서 처리한 에러 : {}", e.getMessage());
        return ResponseUtil.errorResponse(SPRING_SECURITY_ERROR);
    }
}
