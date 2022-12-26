package com.team2.levelog.post.service;


import com.team2.levelog.comment.dto.CommentResponseDto;
import com.team2.levelog.comment.entity.Comment;
import com.team2.levelog.comment.repository.CommentRepository;
import com.team2.levelog.global.GlobalResponse.CustomException;
import com.team2.levelog.global.GlobalResponse.code.ErrorCode;
import com.team2.levelog.image.entity.Image;
import com.team2.levelog.image.repository.ImageRepository;
import com.team2.levelog.post.dto.PostLikesResponseDto;
import com.team2.levelog.post.dto.*;
import com.team2.levelog.post.entity.Likes;
import com.team2.levelog.post.repository.LikesRepository;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.team2.levelog.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 1. 기능      :   Post 관련 모든 비즈니스 로직
// 2. 작성자     :   홍윤재
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final ImageRepository imageRepository;

    //게시글 생성하기
    @Transactional
    public PostResponseDto addPost(PostRequestDto productRequestDto, User user, List<String> imageFiles){
        Post post = postRepository.save(new Post(productRequestDto,user));                   // 저장소에 입력 받은 데이터 저장 // save()때문에 @Transactional 을 사용하지 않아도 됨
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();                 // 댓글을 dto로 감쌈
        for (Comment comment : post.getCommentList()) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        // db에 url 저장
        List<String> imageList = new ArrayList<>();
        for(String imageFile : imageFiles) {
            Image image = new Image(imageFile, post);
            imageRepository.save(image);
            imageList.add(image.getImageFile());
        }
        return new PostResponseDto(post ,commentResponseDtoList);
    }

    // 메인페이지 게시글 보기
    @Transactional
    public List<PostMainPageDto> getMainPage(){
        // 생성 시간 기준으로 모든 포스트 데이터를 DB -> postList 객체에 저장
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        // Dto 리스트 미리 선언
        List<PostMainPageDto> postMainPageDtoList = new ArrayList<>();
        for(Post post : postList){
            // postList에 담긴 데이터들을 하나씩 Dto 리스트에 담아줌
            List<Image> imageList = imageRepository.findByPost(post);
            for (Image image : imageList) {
                postMainPageDtoList.add(new PostMainPageDto(post, image));
            }
        }
        return postMainPageDtoList;
    }

    // 개인 블로그 게시글 전체 보기
    public List<PostBlogDto> getPosts(Long id) {
        List<Post> postList = postRepository.findAllByUserId(id);
        List<PostBlogDto> postBlogDtoList = new ArrayList<>();
        for (Post post : postList) {
            List<Image> imageList = imageRepository.findByPost(post);
            for (Image image : imageList) {
                postBlogDtoList.add(new PostBlogDto(post, image));
            }
        }
        return postBlogDtoList;
    }

    //개인 블로그 게시글 상세페이지
    public PostResponseDto getPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        Image image = imageRepository.findByPostId(post.getId());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : post.getCommentList()) {
            List<CommentResponseDto> childCommentList = new ArrayList<>();
            if(comment.getChildren()==null){
                for (Comment parentComment : comment.getParent()){
                    if (id.equals(parentComment.getPost().getId())) {
                        childCommentList.add(new CommentResponseDto(parentComment));
                    }
                }
                commentResponseDtoList.add(new CommentResponseDto(comment,childCommentList));
            }
        }
        return new PostResponseDto(post, image, commentResponseDtoList);
    }

    // 포스트 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        if(user.getId().equals(post.getUser().getId())) {           // 작성자 아이디가 현재 로그인한 아이디와 같은지 확인
            post.update(postRequestDto);
            return new PostResponseDto(post);
        } else {
            throw new CustomException(ErrorCode.NO_ACCESS);
        }
    }

    // 포스트 삭제
    public void deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        if(user.getId().equals(post.getUser().getId())) {
            postRepository.delete(post);
        }else {
            throw new CustomException(ErrorCode.NO_ACCESS);
        }
    }

    // 포스트 좋아요 기능
    @Transactional
    public PostLikesResponseDto postLike(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        if(likesRepository.findByPostAndUser(post, user).isPresent()) {
            post.update_count(-1);
            likesRepository.deleteByPostAndUser(post, user);
        } else {
            post.update_count(1);
            likesRepository.save(new Likes(user, post));
        }
        return new PostLikesResponseDto(post.getCount());
    }
}
