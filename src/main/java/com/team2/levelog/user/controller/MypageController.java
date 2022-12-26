package com.team2.levelog.user.controller;

import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.security.UserDetailsImpl;
import com.team2.levelog.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MypageController {

    private final UserService userService;

    @GetMapping("mypage")
    public ResponseEntity<?> getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseUtil.successResponse(userService.)
    }
}
