package com.team2.levelog.post.entity;

import com.team2.levelog.post.entity.Post;
import com.team2.levelog.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                                       // 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;                                                     // User와 연관 관계 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Id", nullable = false)
    private Post post;                                                     // Post와 연관 관계 설정

    public Likes(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
