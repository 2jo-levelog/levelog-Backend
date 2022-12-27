package com.team2.levelog.user.service;

import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.TestDto;
import com.team2.levelog.global.jwt.JwtUtil;
import com.team2.levelog.image.repository.ImageRepository;
import com.team2.levelog.image.service.S3Service;
import com.team2.levelog.post.repository.LikesRepository;
import com.team2.levelog.post.repository.PostRepository;
import com.team2.levelog.user.dto.DupRequestCheck;
import com.team2.levelog.user.dto.SigninRequestDto;
import com.team2.levelog.user.dto.SignUpRequestDto;
import com.team2.levelog.user.dto.UserInfoDto;
import com.team2.levelog.user.entity.User;
import com.team2.levelog.user.entity.UserRoleEnum;
import com.team2.levelog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 1. 기능   : 유저 서비스
// 2. 작성자 : 서혁수
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final JwtUtil jwtUtil;
    private final S3Service s3Service;

    // 회원가입
    @Transactional
    public void signUp(SignUpRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        // 1. 중복 여부 검사
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new CustomException(ErrorCode.EXIST_NICKNAME);
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        String imgUrl = s3Service.uploadOne(multipartFile);

        User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, imgUrl, UserRoleEnum.USER);
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
        postRepository.deleteByUserNickname(user.getNickname());
        likesRepository.deleteByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }

    public UserInfoDto getUserInfo(User user){
        return new UserInfoDto(user.getEmail(), user.getNickname(), user.getThumbImg());
    }

}