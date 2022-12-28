package com.team2.levelog.post.dto;

import com.team2.levelog.comment.dto.CommentResponseDto;
import com.team2.levelog.image.dto.ImageResponseDto;
import com.team2.levelog.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;                                                       // 고유 ID
    private String title;                                                  // 포스트 타이틀
    private String content;                                                // 포스트 내용
    private String nickname;                                               // 작성자 닉네임
    private int cmtCnt;                                                    // 댓글 갯수
    private int likeCnt;                                                   // 좋아요 갯수
    private LocalDateTime createdAt;                                       // 작성 시간
    private LocalDateTime modifiedAt;                                      // 수정 시간
    private List<ImageResponseDto> imageList = new ArrayList<>();          // 이미지 리스트
    private List<CommentResponseDto> commentList = new ArrayList<>();      //댓글 리스트


    public PostResponseDto(Post post, List<CommentResponseDto> commentResponseDtos){
        this.id           =     post.getId();
        this.title        =     post.getTitle();
        this.content      =     post.getContent();
        this.nickname     =     post.getUser().getNickname();
        this.cmtCnt       =     post.getCommentList().size();
        this.likeCnt      =     post.getCount();
        this.createdAt    =     post.getCreatedAt();
        this.modifiedAt   =     post.getModifiedAt();
        this.commentList  =     commentResponseDtos;
    }

    public PostResponseDto(Post post, List<ImageResponseDto> images, List<CommentResponseDto> commentResponseDtos){
        this.id           =     post.getId();
        this.title        =     post.getTitle();
        this.content      =     post.getContent();
        this.nickname     =     post.getUser().getNickname();
        this.imageList    =     images;
        this.cmtCnt       =     post.getCommentList().size();
        this.likeCnt      =     post.getCount();
        this.createdAt    =     post.getCreatedAt();
        this.modifiedAt   =     post.getModifiedAt();
        this.commentList  =     commentResponseDtos;
    }

    public PostResponseDto(Post post){
        this.id           =     post.getId();
        this.title        =     post.getTitle();
        this.content      =     post.getContent();
        this.likeCnt      =     post.getCount();
        this.nickname     =     post.getUser().getNickname();
        this.createdAt    =     post.getCreatedAt();
        this.modifiedAt   =     post.getModifiedAt();
    }

    public void update_cmtCnt(){
        this.cmtCnt      =     cmtCnt + 1;
    }
}
