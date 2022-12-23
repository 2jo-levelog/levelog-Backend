package com.team2.levelog.config;

import com.team2.levelog.global.jwt.JwtAuthFilter;
import com.team2.levelog.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// 1. 기능   : Spring Security 설정
// 2. 작성자 : 서혁수
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    // 암호화 타입 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 모든 static 리소스 접근 허가
        return (web -> web.ignoring().requestMatchers(PathRequest
//                        .toStaticResources().atCommonLocations()));
                .toH2Console()));     // h2 사용시 이것을 사용
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // JWT 방식 사용하기 때문에 세션 방식 사용 X
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.httpBasic().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers("/api/auth/**", "/images").permitAll()
                .anyRequest().authenticated()

                // corsConfigurationSource 적용
                .and()
                .cors()

                .and()
                .addFilterBefore(new JwtAuthFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS 이슈
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // 접근 가능한 출처
        config.addAllowedOrigin("http://localhost:3000");

        // 클라이언트가 접근 가능한 헤더 지정 (토큰 사용 가능하게)
        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

        // 본 요청에 허용할 HTTP method
        config.addAllowedMethod("*");

        // 본 요청에 허용할 HTTP header
        config.addExposedHeader("*");
        
        // 브라우저에서 인증 관련 정보들을 요청에 담을 수 있도록 허가
        config.setAllowCredentials(true);

        // allowedOrigin 의 값이 * (즉, 모두 허용)이 설정될 수 없도록 검증
        config.validateAllowCredentials();

        // 설정을 적용할 경로 지정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}