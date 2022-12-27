package com.team2.levelog.post.controller;

import com.team2.levelog.global.GlobalResponse.ResponseUtil;
import com.team2.levelog.global.GlobalResponse.code.SuccessCode;
import com.team2.levelog.global.security.UserDetailsImpl;
import com.team2.levelog.image.service.S3Service;
import com.team2.levelog.post.dto.PostLikesResponseDto;
import com.team2.levelog.post.dto.PostBlogDto;
import com.team2.levelog.post.dto.PostMainPageDto;
import com.team2.levelog.post.dto.PostRequestDto;
import com.team2.levelog.post.dto.PostResponseDto;
import com.team2.levelog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @RequestPart(value = "multipartFile", required = false) List<MultipartFile> multipartFiles,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        if(multipartFiles==null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List<String> imageFiles = s3Service.upload(multipartFiles);
        return ResponseEntity.ok(postService.addPost(postRequestDto, userDetails.getUser(), imageFiles));
    }

    @GetMapping("/main")
    public ResponseEntity<?> getMainPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseUtil.successResponse(postService.getMainPage(pageable));
    }

    @GetMapping("/users/{userNickname}/posts")
    public ResponseEntity<?> getPosts(@PathVariable String userNickname, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseUtil.successResponse(postService.getPosts(userNickname, pageable));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getpost(@PathVariable Long id){
        return ResponseUtil.successResponse(postService.getPost(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestPart(value = "key") PostRequestDto postRequestDto,
                                        @RequestPart(value = "multipartFile", required = false) List<MultipartFile> multipartFiles,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return ResponseUtil.successResponse(postService.updatePost(id, postRequestDto, userDetails.getUser(), multipartFiles));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseUtil.successResponse(SuccessCode.DELETE_OK);
    }
    
    @PostMapping("/posts/{id}/likes")
    public ResponseEntity<?> postlike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.successResponse(postService.postLike(id, userDetails.getUser()));
    }
}
