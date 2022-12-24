package com.team2.levelog.comment.entity;

import com.team2.levelog.comment.dto.CommentRequestDto;
import com.team2.levelog.global.timestamped.Timestamped;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 1. 기능 : 댓글, 대댓글 Entity
// 2. 작성자 : 조소영
@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String comment;

    @Column
    private int depth;

    // 댓글 (고아객체 삭제, 영속성 전이로 자식인 대댓글의 생명주기 관리)
    @OneToMany(mappedBy = "children", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Comment> parent = new ArrayList<>();

    // 대댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_Id")
    private Comment children;

    public Comment(Post post, CommentRequestDto commentRequestDto, User user){
        this.user = user;
        this.post = post;
        this.nickname = user.getNickname();
        this.comment = commentRequestDto.getComment();
    }

    public Comment(Post post, Comment children, CommentRequestDto commentRequestDto, User user){
        this.user = user;
        this.post = post;
        this.children = children;
        this.nickname = user.getNickname();
        this.comment = commentRequestDto.getComment();
    }

    public void update(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }
}
