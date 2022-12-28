package com.team2.levelog.image.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ImageRequestDto {
    private List<MultipartFile> files;
}
