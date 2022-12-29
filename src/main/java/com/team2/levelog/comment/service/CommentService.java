package com.team2.levelog.comment.service;

import com.team2.levelog.comment.dto.CommentRequestDto;
import com.team2.levelog.comment.entity.Comment;
import com.team2.levelog.comment.repository.CommentRepository;
import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.post.repository.PostRepository;
import com.team2.levelog.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 1. 기능 : 댓글 비즈니스 로직
// 2. 작성자 : 조소영
@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    public void createComment(Long postId, CommentRequestDto commentRequestDto, User user){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Comment newComment = new Comment(post, commentRequestDto, user);
        commentRepository.save(newComment);
    }

    // 대댓글 작성
    public void createReply(Long postId, Long commentsId, CommentRequestDto commentRequestDto, User user){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        Comment newComment = new Comment(post, comment, commentRequestDto, user);
        commentRepository.save(newComment);
    }

    // 댓글, 대댓글 수정
    @Transactional
    public void modifyComment(Long commentsId, CommentRequestDto commentRequestDto, User user){
        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if(!user.getNickname().equals(comment.getNickname())){
            throw new CustomException(ErrorCode.NO_ACCESS);
        }

        comment.update(commentRequestDto);
    }

    // 댓글, 대댓글 삭제
    public void deleteComment(Long commentsId, User user){
        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if(!user.getNickname().equals(comment.getNickname())){
            throw new CustomException(ErrorCode.NO_ACCESS);
        }

        commentRepository.delete(comment);
    }
}
