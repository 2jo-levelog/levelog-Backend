package com.team2.levelog.post.dto;

import com.team2.levelog.comment.dto.CommentResponseDto;
import com.team2.levelog.post.entity.Likes;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.post.repository.LikesRepository;
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
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private int cmtCnt;
    private int likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();        //댓글 리스트


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
