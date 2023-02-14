package com.team2.levelog.image.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

// 1. 기능   : 이미지 첨부시 입력값
// 2. 작성자 : 박소연
@Getter
public class ImageRequestDto {
    private List<MultipartFile> files;
}
