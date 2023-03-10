package com.team2.levelog.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.levelog.global.GlobalResponse.GlobalResponseDto;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        String token = jwtUtil.resolveToken(request);

        // 2. 토큰 유효 판별
        if(token != null) {
            if(!jwtUtil.validateToken(token)){
                response.setStatus(ErrorCode.INVALID_TOKEN.getHttpStatus().value());
                response.setContentType("application/json; charset=UTF-8");
                try {
                    String json = new ObjectMapper().writeValueAsString(new GlobalResponseDto(ErrorCode.INVALID_TOKEN));
                    response.getWriter().write(json);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                return;
            }
            // 3. 토큰이 유효하다면 토큰에서 정보를 가져와 Authentication 에 세팅
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication((String)info.get("nickname"));
        }
        // 4. 다음 필터로 넘어간다
        filterChain.doFilter(request, response);
    }

    // 권한 설정
    public void setAuthentication(String nickname) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(nickname);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
