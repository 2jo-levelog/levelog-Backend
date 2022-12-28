package com.team2.levelog.user.service;

import com.team2.levelog.global.Email.EmailService;
import com.team2.levelog.global.Email.EmailServiceImpl;
import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.Redis.RedisUtil;
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

import static com.team2.levelog.global.GlobalResponse.code.ErrorCode.EMAIL_CONFIRM_NOT_FOUND;

// 1. 기능   : 유저 서비스
// 2. 작성자 : 서혁수
// 3. 수정사항 : email 인증 절차 by 조소영
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final EmailServiceImpl emailServiceImpl;

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

    // 이메일 인증 전송 및 임시 회원가입(Redis에 저장)
    public void emailSignUp(SignUpRequestDto requestDto) throws Exception {
        // 1. 중복 여부 검사
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new CustomException(ErrorCode.EXIST_NICKNAME);
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, requestDto.getProfileImg(), UserRoleEnum.USER);

        String emailAuthCode = emailServiceImpl.sendSimpleMessage(requestDto.getEmail());

        redisUtil.set(emailAuthCode, user, 10);
    }

    // 메일로 확인시 Redis에 기록된 내용이 MySQL로 저장되고 Redis내용은 삭제
    public void emailConfirm(String emailConfirmCode){
        User user = (User) redisUtil.get(emailConfirmCode);

        if(user == null){
            throw new CustomException(EMAIL_CONFIRM_NOT_FOUND);
        } else {
            userRepository.save(user);
            redisUtil.delete(emailConfirmCode);
        }
    }
}
