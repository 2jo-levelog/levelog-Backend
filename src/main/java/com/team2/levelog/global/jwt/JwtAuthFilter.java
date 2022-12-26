package com.team2.levelog.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.GlobalResponseDto;
import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.levelog.global.TestDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 1. 기능   : JWT 인증 필터 / 토큰 유효성 검사
// 2. 작성자 : 서혁수
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 요청에서 받아온 토큰의 문자열 받기
        String acToken = jwtUtil.resolveAcToken(request);
        String rsToken = jwtUtil.resolveRsToken(request);

        // 2. 토큰 유효 판별
        // accessToken null 이면 rsToken 을 발급받는 식이다.
        if(acToken != null) {
            if(!jwtUtil.validateToken(acToken)){
                throw new IllegalArgumentException("Access Token Error");
            }
            // 3. 토큰이 유효하다면 토큰에서 정보를 가져와 Authentication 에 세팅
            setAuthentication(jwtUtil.getNickInfoFromToken(acToken));
        } else if (rsToken != null) {
            if (!jwtUtil.rsTokenValidation(rsToken)) {
                throw new IllegalArgumentException("Refresh Token Error");
            }
            setAuthentication(jwtUtil.getNickInfoFromToken(rsToken));
        }
        // 4. 다음 필터로 넘어간다
        filterChain.doFilter(request, response);
    }

    // 권한 설정
    public void setAuthentication(String nickname) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication = jwtUtil.createAuthentication(nickname);
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
        Authentication authentication = jwtUtil.createAuthentication(nickname);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
//        response.setStatus(status.value());
//        response.setContentType("application/json");
//        try {
//            String json = new ObjectMapper().writeValueAsString(new TestDto(status.value(), msg));
//            response.getWriter().write(json);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
}
