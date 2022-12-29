package com.team2.levelog.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 1. 기능      :   게시글 좋아요 총 갯수 반환값
// 2. 작성자    :   홍윤재
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikesResponseDto{
    private int count;
}
