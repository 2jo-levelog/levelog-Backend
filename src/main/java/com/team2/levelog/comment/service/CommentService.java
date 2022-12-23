package com.team2.levelog.comment.service;

import com.team2.levelog.comment.dto.CommentRequestDto;
import com.team2.levelog.comment.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    // 댓글 작성
    public void createComment(Long postId, CommentRequestDto commentRequestDto){

    }

    // 대댓글 작성
    public void createReply(Long postId, Long commentsId, CommentRequestDto commentRequestDto){

    }

    // 댓글, 대댓글 수정
    public void modifyComment(Long commentsId, CommentRequestDto commentRequestDto){

    }

    // 댓글, 대댓글 삭제
    public void deleteComment(Long commentsId){

    }
}
