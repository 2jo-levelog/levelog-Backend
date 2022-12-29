package com.team2.levelog.comment.dto;

import com.team2.levelog.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 1. 기능 : 댓글 구성요소 반환값
// 2. 작성자 : 조소영
@Getter
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String comment;
    private List<CommentResponseDto> children = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private String profileImg;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.nickname = comment.getNickname();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public CommentResponseDto(Comment comment, String profileImg){
        this.id = comment.getId();
        this.nickname = comment.getNickname();
        this.comment = comment.getComment();
        this.profileImg = profileImg;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public CommentResponseDto(Comment comment, List<CommentResponseDto> commentResponseDtoList, String profileImg){
        this.id = comment.getId();
        this.nickname = comment.getNickname();
        this.comment = comment.getComment();
        this.children = commentResponseDtoList;
        this.profileImg = profileImg;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
