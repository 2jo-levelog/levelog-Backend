package com.team2.levelog.post.dto;

import com.team2.levelog.post.entity.Likes;
import lombok.Getter;

// 1. 기능      :   게시글 좋아요 구성요소 반환값
// 2. 작성자    :   홍윤재
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
