package com.team2.levelog.comment.controller;

import com.team2.levelog.comment.dto.CommentRequestDto;
import com.team2.levelog.comment.service.CommentService;
import com.team2.levelog.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

// 1. 기능 : 댓글 CUD, 대댓글 CUD (Read기능은 Comment Service의 메소드로 Post에 사용예정)
// 2. 작성자 : 조소영
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public void createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.createComment(postId, commentRequestDto, userDetails.getUser());
    }

    // 대댓글 작성
    @PostMapping("/{postId}/comments/{commentsId}")
    public void createReply(
            @PathVariable Long postId,
            @PathVariable Long commentsId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.createReply(postId, commentsId, commentRequestDto, userDetails.getUser());
    }

    // 댓글, 대댓글 수정
    @PutMapping("/comments/{commentsId}")
    public void modifyComment(
            @PathVariable Long commentsId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.modifyComment(commentsId, commentRequestDto, userDetails.getUser());
    }

    // 댓글, 대댓글 삭제
    @DeleteMapping("/comments/{commentsId}")
    public void deleteComment(
            @PathVariable Long commentsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(commentsId, userDetails.getUser());
    }
}
