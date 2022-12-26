package com.team2.levelog.post.dto;

import com.team2.levelog.global.timestamped.Timestamped;
import com.team2.levelog.image.entity.Image;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ImageResponseDto {
    private Long id;
    private String imgUrl;

    public ImageResponseDto(Image image) {
        this.id          =     image.getId();
        this.imgUrl      =     image.getImageFile();
    }

}
