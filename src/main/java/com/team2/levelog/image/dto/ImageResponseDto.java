package com.team2.levelog.image.dto;

import com.team2.levelog.image.repository.entity.PostImage;
import lombok.Getter;

// 1. 기능   : 이미지 첨부 반환값
// 2. 작성자 : 박소연
@Getter
public class ImageResponseDto {
    private Long id;
    private String imgUrl;

    public ImageResponseDto(PostImage postImage) {
        this.id          =     postImage.getId();
        this.imgUrl      =     postImage.getImagePath();
    }

}
