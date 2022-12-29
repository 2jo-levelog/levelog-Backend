package com.team2.levelog.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 1. 기능      :   게시글 작성시 입력값
// 2. 작성자    :   홍윤재
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;                        // 포스트 타이틀
    private String content;                      // 포스트 내용
//    private List<ImageResponseDto> imageList;
}
