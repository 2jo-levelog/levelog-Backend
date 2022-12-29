package com.team2.levelog.post.entity;

import com.team2.levelog.comment.entity.Comment;
import com.team2.levelog.global.timestamped.Timestamped;
import com.team2.levelog.image.repository.entity.PostImage;
import com.team2.levelog.post.dto.PostRequestDto;
import com.team2.levelog.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                                          // 고유 ID
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                                                        // User 와 연관 관계 설정
    @Column
    private String title;                                                     // 포스트 타이틀
    @Column
    private String content;                                                   // 포스트 내용
    @Column
    private String nickname;                                                  // 작성자 닉네임
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)               // 연관된 post가 삭제되면 함께 삭제되도록 cascade 설정
    private List<Comment> commentList = new ArrayList<>();                    // 댓글 리스트
    @Column(nullable = false)
    private int count;                                                        // 좋아요 갯수
    @OneToMany(
            mappedBy = "post", cascade = {CascadeType.REMOVE},
            orphanRemoval = true)
    private List<PostImage> postImageList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user) {
        this.user       =    user;
        this.title      =    postRequestDto.getTitle();
        this.content    =    postRequestDto.getContent();
        this.nickname   =    user.getNickname();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title      =    postRequestDto.getTitle();
        this.content    =    postRequestDto.getContent();
    }

    public void update_count(int n){
        this.count = count + n;
    }
}
