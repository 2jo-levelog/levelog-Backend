package com.team2.levelog.user.service;

import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.TestDto;
import com.team2.levelog.global.jwt.JwtUtil;
import com.team2.levelog.user.dto.DupRequestCheck;
import com.team2.levelog.user.dto.SigninRequestDto;
import com.team2.levelog.user.dto.SignUpRequestDto;
import com.team2.levelog.user.entity.User;
import com.team2.levelog.user.entity.UserRoleEnum;
import com.team2.levelog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new CustomException(ErrorCode.EXIST_NICKNAME);
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, requestDto.getProfileImg(), UserRoleEnum.USER);

        userRepository.save(user);
    }

    // 폼 로그인
    public void login(SigninRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOTEXIST_EMAIL)
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.BAD_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getNickname(), UserRoleEnum.USER.getAuthority()));
    }

    public boolean dupCheckEmail(DupRequestCheck requestCheck) {
        if (userRepository.existsByEmail(requestCheck.getEmail())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean dupCheckNick(DupRequestCheck requestCheck) {
        if (userRepository.existsByNickname(requestCheck.getNickname())) {
            return true;
        } else {
            return false;
        }
    }

    // 회원탈퇴
    public void deleteUser(User user) {

    }

    // 이메일인증 회원가입
    public void emailSignUp(SignUpRequestDto requestDto) {
        // 1. 중복 여부 검사
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new CustomException(ErrorCode.EXIST_NICKNAME);
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, requestDto.getProfileImg(), UserRoleEnum.USER);

        userRepository.save(user);
    }
}
