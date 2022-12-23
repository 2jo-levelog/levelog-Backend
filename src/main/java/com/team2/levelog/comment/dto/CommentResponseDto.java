package com.team2.levelog.comment.dto;

import com.team2.levelog.comment.entity.Comment;
import com.team2.levelog.global.timestamped.Timestamped;
import lombok.Getter;

import java.util.List;
@Getter
public class CommentResponseDto extends Timestamped {
    private Long id;
    private String nickname;
    private String comment;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.nickname = comment.getNickname();
        this.comment = comment.getComment();
    }
}
