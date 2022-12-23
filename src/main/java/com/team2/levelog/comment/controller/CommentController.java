package com.team2.levelog.comment.controller;

import com.team2.levelog.comment.dto.CommentRequestDto;
import com.team2.levelog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// 1. 기능 : 댓글 CUD, 대댓글 CUD (Read기능은 Comment Service의 메소드로 Post에 사용예정)
// 2. 작성자 : 조소영
@RestController
@RequestMapping("/api/users/{userId}/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postId}/comments")
//    public void createComment(
//            @PathVariable Long postId,
//            @RequestBody CommentRequestDto commentRequestDto,

    public void createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto){
//        commentService.createComment(postId, commentRequestDto, userDetails.getUser());
        commentService.createComment(postId, commentRequestDto);
    }

    // 대댓글 작성
    @PostMapping("/{postId}/comments/{commentsId}")
//    public void createReply(
//            @PathVariable Long postId,
//            @PathVariable Long commentsId,
//            @RequestBody CommentRequestDto commentRequestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails){
    public void createReply(@PathVariable Long postId, @PathVariable Long commentsId, @RequestBody CommentRequestDto commentRequestDto){
//        commentService.createReply(postId, commentsId, commentRequestDto, userDetails.getUser());
        commentService.createReply(postId, commentsId, commentRequestDto);
    }

    // 댓글, 대댓글 수정
    @PutMapping("/comments/{commentsId}")
//    public void modifyComment(
//            @PathVariable Long commentsId,
//            @RequestBody CommentRequestDto commentRequestDto,
//            @AuthenticationPrincipal UserDetailsImpl userDetails){
    public void modifyComment(@PathVariable Long commentsId, @RequestBody CommentRequestDto commentRequestDto){
//        commentService.modifyComment(commentsId, commentRequestDto, userDetails.getUser());
        commentService.modifyComment(commentsId, commentRequestDto);

    }

    // 댓글, 대댓글 삭제
    @DeleteMapping("/comments/{commentsId}")
//    public void deleteComment(
//            @PathVariable Long commentsId
//            @AuthenticationPrincipal UserDetailsImpl userDetails){
    public void deleteComment(@PathVariable Long commentsId){
//        commentService.deleteComment(commentsId, userDetails.getUser());
        commentService.deleteComment(commentsId);
    }


}
