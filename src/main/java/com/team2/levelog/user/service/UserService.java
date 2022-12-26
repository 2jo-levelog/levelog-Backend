package com.team2.levelog.user.service;

import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.TestDto;
import com.team2.levelog.global.jwt.JwtUtil;
import com.team2.levelog.global.jwt.RefreshToken;
import com.team2.levelog.global.jwt.RefreshTokenRepository;
import com.team2.levelog.global.jwt.TokenDto;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

// 1. 기능   : 유저 서비스
// 2. 작성자 : 서혁수
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    public void signUp(SignUpRequestDto requestDto) {
        // 1. 중복 여부 검사
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("중복 이메일 존재");
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new IllegalArgumentException("중복 닉네임 존재");
        }

        // password 값 인코딩 후 Dto 에 재주입 후 Dto 를 Entity 로 변환
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, requestDto.getProfileImg(), UserRoleEnum.USER);
        userRepository.save(user);
    }

    // 폼 로그인
    @Transactional
    public void login(SigninRequestDto requestDto, HttpServletResponse response) {
        // userId 로 user 정보 호출
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 이메일 입니다.")
        );

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // email 과 nickname 값을 포함한 토큰 생성 후 tokenDto 에 저장
        TokenDto tokenDto = jwtUtil.createAllToken(requestDto.getEmail(), user.getNickname());

        // nickname 값에 해당하는 refreshToken 을 DB 에서 가져옴
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserNickname(user.getNickname());

        // isPresent() 메소드는 Optional 객체의 값이 null 인지 여부
        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), user.getNickname());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

    }



    public ResponseEntity<TestDto> dupCheckEmail(DupRequestCheck requestCheck) {
        if (userRepository.existsByEmail(requestCheck.getEmail())) {
            return ResponseEntity.badRequest().body(new TestDto(400, "중복 이메일 존재"));
        } else {
            return ResponseEntity.ok().body(new TestDto(200, "사용 가능한 이메일 입니다."));
        }
    }

    public ResponseEntity<TestDto> dupCheckNick(DupRequestCheck requestCheck) {
        if (userRepository.existsByNickname(requestCheck.getNickname())) {
            return ResponseEntity.badRequest().body(new TestDto(400, "중복 닉네임 존재"));
        } else {
            return ResponseEntity.ok().body(new TestDto(200, "사용 가능한 닉네임 입니다."));
        }
    }

    public void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.AC_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.RS_TOKEN, tokenDto.getRefreshToken());
    }

    // 회원탈퇴
    public void deleteUser(User user) {

    }
}
