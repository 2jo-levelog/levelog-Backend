package com.team2.levelog.comment.service;

import com.team2.levelog.comment.dto.CommentRequestDto;
import com.team2.levelog.comment.entity.Comment;
import com.team2.levelog.comment.repository.CommentRepository;
import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.post.repository.PostRepository;
import com.team2.levelog.user.entity.User;
import com.team2.levelog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final UserRepository userRepository; // 테스트를 위해 임시로 유저 끌어오기위한 의존성주입

//    // 댓글, 대댓글 조회
//    public List<Comment> getComment(Long postId){
//        List<Comment> commentList = commentRepository.findAllByPost_Id(postId);
//
//
//        return
//    }


    // 댓글 작성
    public void createComment(Long postId, CommentRequestDto commentRequestDto, User user){
//    public void createComment(Long postId, CommentRequestDto commentRequestDto){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

//        User user = userRepository.findById(1L).orElse(null); // 테스트 유저

        Comment newComment = new Comment(post, commentRequestDto, user);
        commentRepository.save(newComment);
    }

    // 대댓글 작성
    public void createReply(Long postId, Long commentsId, CommentRequestDto commentRequestDto, User user){
//    public void createReply(Long postId, Long commentsId, CommentRequestDto commentRequestDto){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

//        User user = userRepository.findById(1L).orElse(null); // 테스트 유저

        Comment newComment = new Comment(post, comment, commentRequestDto, user);
        commentRepository.save(newComment);
    }

    // 댓글, 대댓글 수정
    @Transactional
    public void modifyComment(Long commentsId, CommentRequestDto commentRequestDto, User user){
//    public void modifyComment(Long commentsId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        comment.update(commentRequestDto);
    }

    // 댓글, 대댓글 삭제
    public void deleteComment(Long commentsId, User user){
//    public void deleteComment(Long commentsId){
        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                ()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        commentRepository.delete(comment);
    }
}
