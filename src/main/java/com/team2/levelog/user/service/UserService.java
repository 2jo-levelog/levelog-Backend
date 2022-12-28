package com.team2.levelog.user.service;

import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.global.jwt.JwtUtil;
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
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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

        // 2. 클라이언트로부터 받아온 비밀번호를 인코딩 해서 가져오기
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        // 3. s3 에 이미지 업로드를 하고 해당 이미지 URL 가져오기
        //      - 아무런 이미지를 넣지 않으면 null 값이 들어간다.
        String imgUrl = null;

        // 4. 새롭게 만들 빈 User 객체 생성
        User user = new User();

        // 5. 받아온 값들로 새로운 User 객체를 만들기
        try {
            // 6. 회원가입시 프로필 이미지를 등록하면 s3에 업로드 및 새로운 user 객체 생성
            if (!multipartFile.isEmpty()) {
                imgUrl = s3Service.uploadOne(multipartFile);
                user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, imgUrl, UserRoleEnum.USER);
            }
        } catch (NullPointerException e) {
            // 7. 이미지를 등록하지 않을 경우 빈값으로 들어간다.
            user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, UserRoleEnum.USER);
        }

        // 8. DB 에 새로운 유저정보 넣어주기
        userRepository.save(user);

//        if (!multipartFile.isEmpty()) {
//            imgUrl = s3Service.uploadOne(multipartFile);
//            User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, imgUrl, UserRoleEnum.USER);
//            userRepository.save(user);
//        } else {
//            User user = new User(requestDto.getEmail(), requestDto.getNickname(), encodePassword, UserRoleEnum.USER);
//            userRepository.save(user);
//        }

    }

    // 폼 로그인
    public void login(SigninRequestDto requestDto, HttpServletResponse response) {
        // 1. 유저 이메일 기준으로 유저 정보 찾아와서
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NOTEXIST_EMAIL)
        );

        // 2. 비밀번호호가 일치하는지 검증한다.
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.BAD_PASSWORD);
        }

        // 3. 로그인 성공 및 토큰을 발급받아서 가져온다.
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getNickname(), UserRoleEnum.USER.getAuthority()));
    }

    // 이메일 중복 체크 버튼용
    public boolean dupCheckEmail(DupRequestCheck requestCheck) {
        if (userRepository.existsByEmail(requestCheck.getEmail())) {
            return true;
        } else {
            return false;
        }
    }

    // 닉네임 중복 체크 버튼용
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

    // 프로필 정보 가져오기
    public UserInfoDto getUserInfo(User user) {
        return new UserInfoDto(user.getEmail(), user.getNickname(), user.getThumbImg());
    }

}