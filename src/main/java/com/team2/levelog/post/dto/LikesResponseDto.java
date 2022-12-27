package com.team2.levelog.post.dto;

import com.team2.levelog.post.entity.Likes;
import lombok.Getter;

@Getter
public class LikesResponseDto {
    private Long likeId;     // 고유 ID
    private Long postId;     // 포스트 ID
    private Long userId;     // 작성자 ID

    public LikesResponseDto(Likes like) {
        this.likeId   =   like.getId();
        this.postId   =   like.getPost().getId();
        this.userId   =   like.getUser().getId();
    }
}
