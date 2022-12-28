package com.team2.levelog.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.jwt.JwtUtil;
import com.team2.levelog.user.dto.KakaoResponseDto;
import com.team2.levelog.user.dto.KakaoUserInfoDto;
import com.team2.levelog.user.entity.User;
import com.team2.levelog.user.entity.UserRoleEnum;
import com.team2.levelog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    // kakao 로그인해 사용자 정보 가져오기
    public KakaoResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. 인가 코드에서 액세스 토큰 얻기
//        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
//        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(code);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. JWT 토큰 헤더로 반환
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(kakaoUser.getEmail(), kakaoUser.getNickname(), UserRoleEnum.USER.getAuthority()));

        return new KakaoResponseDto(SuccessCode.SIGNIN_OK);
    }

    // "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "3p4RCHG5dD9kX2fJopJQqpjs7EGCTc1i");
        body.add("redirect_uri", "http://localhost:3000/api/user/kakao/callback");
        body.add("code", code);

        // HTTP Entity에 생성한 헤더와 바디 Set
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);

        // Client와 서버끼리 RestTemplate로 주고 받기위한 객체
        // 카카오 권한 서버로 HTTP 요청 보내기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(                  // 카카오 권한 서버에 요청하여 받은 값을 response에 대입
                "https://kauth.kakao.com/oauth/token",                  // 엑세스 토큰을 받기위한 url
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON)에 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);        // String myJson값을 mapper로 읽어 트리 Json형태로 바꾼다
        return jsonNode.get("access_token").asText();                   //필드 access_token에서 값을 가져와 타입을 변환한다
    }

    // getToken 메소드로 얻은 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오 권한 서버로 HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(                  // 카카오 권한 서버에 요청하여 받은 값을 response에 대입
                "https://kapi.kakao.com/v2/user/me",                    // 유저 정보를 받아오기 위한 url
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        System.out.println("responseBody = " + responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String thumbImg = jsonNode.get("properties")
                .get("thumbnail_image").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email, thumbImg);
    }

    // 3. 비회원일 경우 회원가입 또는 기존회원이나 카카오ID 부재일 시 카카오ID 추가
    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입 / 카카오로 로그인시 별도의 비밀번호는 필요없기 때문에 임의의 비밀번호를 삽입한다
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // 카카오 email
                String email = kakaoUserInfo.getEmail();

                kakaoUser = new User(email, kakaoUserInfo.getNickname(), kakaoId, kakaoUserInfo.getThumbImg(), encodedPassword, UserRoleEnum.USER);
            }

            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }
}
