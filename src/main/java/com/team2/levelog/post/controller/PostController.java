package com.team2.levelog.post.controller;

import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.security.UserDetailsImpl;
import com.team2.levelog.image.service.S3Service;
import com.team2.levelog.post.dto.PostRequestDto;
import com.team2.levelog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// 1. 기능      :   Post 관련 컨트롤러
// 2. 작성자    :   홍윤재
@RequiredArgsConstructor       // 생성자 주입
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final S3Service s3Service;

    // 포스트 업로드
//    @PostMapping ("/posts/write")
//    public ResponseEntity<?> addPost(
//            @RequestPart(value = "key") PostRequestDto postRequestDto,
//            @RequestPart(value = "multipartFile", required = false) List<MultipartFile> multipartFiles,
//            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
//
//        return ResponseEntity.ok(postService.addPost(postRequestDto, userDetails.getUser(), multipartFiles));
//    }
    @PostMapping ("/posts/write")
    public ResponseEntity<?> addPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(postService.addPost(postRequestDto, userDetails.getUser()));
    }

    // 메인 페이지 게시글 리스트 불러오기
    @GetMapping("/main")
    public ResponseEntity<?> getMainPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseUtil.successResponse(postService.getMainPage(pageable));
    }

    // 회원 개인 페이지 게시글 리스트 불러오기
    @GetMapping("/users/{userNickname}/posts")
    public ResponseEntity<?> getPosts(@PathVariable String userNickname, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseUtil.successResponse(postService.getPosts(userNickname, pageable));
    }

    // 게시글 상세페이지
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getpost(@PathVariable Long id, HttpServletRequest request){
        return ResponseUtil.successResponse(postService.getPost(id, request));
    }

    // 게시글 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestBody PostRequestDto postRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return ResponseUtil.successResponse(postService.updatePost(id, postRequestDto, userDetails.getUser()));
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseUtil.successResponse(SuccessCode.DELETE_OK);
    }

    // 게시글 좋아요
    @PostMapping("/posts/{id}/likes")
    public ResponseEntity<?> postlike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.successResponse(postService.postLike(id, userDetails.getUser()));
    }
}
