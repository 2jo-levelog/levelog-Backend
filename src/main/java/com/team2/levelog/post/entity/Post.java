package com.team2.levelog.post.entity;

import com.team2.levelog.comment.entity.Comment;
import com.team2.levelog.global.timestamped.Timestamped;
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
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_ㅑd")
    private User user;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String nickname;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();
    @Column(nullable = false)
    private int count;

    public Post(PostRequestDto postRequestDto, User user) {
        this.user       =    user;
        this.title      =    title;
        this.content    =    content;
        this.nickname   =    user.getNickname();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title      =    title;
        this.content    =    content;
    }

    public void update_count(int n){
        this.count = count + 1;
    }
}
