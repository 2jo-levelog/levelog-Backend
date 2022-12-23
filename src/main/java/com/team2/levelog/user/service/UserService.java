package com.team2.levelog.user.service;

import com.team2.levelog.global.jwt.JwtUtil;
import com.team2.levelog.user.dto.LoginRequestDto;
import com.team2.levelog.user.dto.SignUpRequestDto;
import com.team2.levelog.user.entity.User;
import com.team2.levelog.user.entity.UserRoleEnum;
import com.team2.levelog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

// 1. 기능   : 유저 서비스
// 2. 작성자 : 서혁수
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signUp(SignUpRequestDto requestDto) {
        // 1. 중복 여부 검사
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("중복 이메일 존재");
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new IllegalArgumentException("중복 닉네임 존재");
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, requestDto.getProfileImg(), UserRoleEnum.USER);
        userRepository.save(user);
    }

    // 폼 로그인
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 이메일 입니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getNickname(), UserRoleEnum.USER.getAuthority()));
    }

    // 회원탈퇴
    public void deleteUser(User user) {

    }
}
