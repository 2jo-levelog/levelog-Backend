package com.team2.levelog.post.dto;

import com.team2.levelog.image.dto.ImageResponseDto;
import com.team2.levelog.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 1. 기능      :   메인페이지 전체 게시글 반환값
// 2. 작성자    :   홍윤재
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostMainPageDto {
    private Long id;                                                       // 고유 ID
    private String title;                                                  // 게시글 타이틀
    private String content;                                                // 게시글 내용
    private String nickname;                                               // 작성자 닉네임
    private int likeCnt;                                                   // 좋아요 갯수
    private int cmtCnt;                                                    // 댓글 갯수
    private LocalDateTime createdAt;                                       // 작성 시간
    private LocalDateTime modifiedAt;                                      // 수정 시간
    private List<ImageResponseDto> imageList = new ArrayList<>();          // 이미지 리스트

    public PostMainPageDto(Post post, List<ImageResponseDto> images){
        this.id           =     post.getId();
        this.title        =     post.getTitle();
        this.content      =     post.getContent();
        this.imageList    =     images;
        this.likeCnt      =     post.getCount();
        this.cmtCnt       =     post.getCommentList().size();
        this.nickname     =     post.getUser().getNickname();
        this.createdAt    =     post.getCreatedAt();
        this.modifiedAt   =     post.getModifiedAt();
    }
}
