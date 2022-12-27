package com.team2.levelog.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;                        // 포스트 타이틀
    private String content;                      // 포스트 내용
    private List<ImageResponseDto> imageDtos;
}
