package com.team2.levelog.comment.service;

import com.team2.levelog.comment.dto.CommentRequestDto;
import com.team2.levelog.comment.entity.Comment;
import com.team2.levelog.comment.repository.CommentRepository;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.post.repository.PostRepository;
import com.team2.levelog.user.entity.User;
import com.team2.levelog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    public void createComment(Long postId, CommentRequestDto commentRequestDto, User user){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 글이 없습니다")
        );

        Comment newComment = new Comment(post, commentRequestDto, user);
        commentRepository.save(newComment);
    }

    // 대댓글 작성
    public void createReply(Long postId, Long commentsId, CommentRequestDto commentRequestDto, User user){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 글이 없습니다")
        );

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 없습니다")
        );

        Comment newComment = new Comment(post, comment, commentRequestDto, user);
        commentRepository.save(newComment);
    }

    // 댓글, 대댓글 수정
    @Transactional
    public void modifyComment(Long commentsId, CommentRequestDto commentRequestDto, User user){
        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 없습니다")
        );

        comment.update(commentRequestDto);
    }

    // 댓글, 대댓글 삭제
    public void deleteComment(Long commentsId, User user){
        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 없습니다")
        );

        commentRepository.delete(comment);
    }
}
