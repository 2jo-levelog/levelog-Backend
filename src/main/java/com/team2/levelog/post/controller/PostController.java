package com.team2.levelog.post.controller;

import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.security.UserDetailsImpl;
import com.team2.levelog.image.service.S3Service;
import com.team2.levelog.post.dto.PostRequestDto;
import com.team2.levelog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// 1. 기능      :   Post 관련 API 컨트롤러 구현
// 2. 작성자     :   홍윤재

@RequiredArgsConstructor       // 생성자 주입
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final S3Service s3Service;

    // S3 업데이트 이후 사용할 맵핑
    @PostMapping ("/posts/write")
    public ResponseEntity<?> addPost(
            @RequestPart(value = "key") PostRequestDto postRequestDto,
            @RequestPart(value = "multipartFile") List<MultipartFile> multipartFiles,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        if(multipartFiles==null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List<String> imageFiles = s3Service.upload(multipartFiles);
        return ResponseEntity.ok(postService.addPost(postRequestDto, userDetails.getUser(), imageFiles));
    }

    @GetMapping("/main")
    public ResponseEntity<?> getMainPage(){
        return ResponseUtil.successResponse(postService.getMainPage());
    }

//    @PostMapping("/users/{id}/posts/write")
//    public ResponseEntity<?> addPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return ResponseUtil.successResponse(postService.addPost(postRequestDto, userDetails.getUser()));
//    }

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<?> getPosts(@PathVariable Long id){
        return ResponseUtil.successResponse(postService.getPosts(id));
    }

    @GetMapping("/users/posts/{id}")
    public ResponseEntity<?> getpost(@PathVariable Long id){
        return ResponseUtil.successResponse(postService.getPost(id));
    }

    @PutMapping("users/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.successResponse(postService.updatePost(id, postRequestDto, userDetails.getUser()));
    }

    @DeleteMapping("users/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseUtil.successResponse(SuccessCode.DELETE_OK);
    }
    
    @PostMapping("users/posts/{id}/likes")
    public ResponseEntity<?> postlike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.successResponse(postService.postLike(id, userDetails.getUser()));
    }
}
