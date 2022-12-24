package com.team2.levelog.post.dto;

import com.team2.levelog.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBlogDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private int likeCnt;
    private int cmtCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostBlogDto(Post post){
        this.id           =     post.getId();
        this.title        =     post.getTitle();
        this.content      =     post.getContent();
        this.likeCnt      =     post.getCount();
        this.cmtCnt       =     post.getCommentList().size();
        this.nickname     =     post.getUser().getNickname();
        this.createdAt    =     post.getCreatedAt();
        this.modifiedAt   =     post.getModifiedAt();
    }
}
