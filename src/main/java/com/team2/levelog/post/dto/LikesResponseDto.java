package com.team2.levelog.post.dto;

import com.team2.levelog.post.entity.Likes;
import lombok.Getter;

@Getter
public class LikesResponseDto {
    private Long likeId;
    private Long postId;
    private Long userId;

    public LikesResponseDto(Likes like) {
        this.likeId   =   like.getId();
        this.postId   =   like.getPost().getId();
        this.userId   =   like.getUser().getId();
    }
}
