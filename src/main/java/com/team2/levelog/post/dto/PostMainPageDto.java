package com.team2.levelog.post.dto;

import com.team2.levelog.image.entity.Image;
import com.team2.levelog.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostMainPageDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private int likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ImageResponseDto> imageList = new ArrayList<>();

    public PostMainPageDto(Post post, List<ImageResponseDto> images){
        this.id           =     post.getId();
        this.title        =     post.getTitle();
        this.content      =     post.getContent();
        this.imageList    =     images;
        this.likeCnt      =     post.getCount();
        this.nickname     =     post.getUser().getNickname();
        this.createdAt    =     post.getCreatedAt();
        this.modifiedAt   =     post.getModifiedAt();
    }
}
