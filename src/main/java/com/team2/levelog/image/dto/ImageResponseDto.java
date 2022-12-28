package com.team2.levelog.image.dto;

import com.team2.levelog.image.entity.PostImage;
import lombok.Getter;

@Getter
public class ImageResponseDto {
    private Long id;
    private String imgUrl;

    public ImageResponseDto(PostImage postImage) {
        this.id          =     postImage.getId();
        this.imgUrl      =     postImage.getImagePath();
    }

}
