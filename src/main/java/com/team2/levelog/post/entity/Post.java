package com.team2.levelog.post.entity;

import com.team2.levelog.global.timestamped.Timestamped;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String title;
    @Column
    private Long price;
    @Column
    private String content;
    @Column
    private String nickname;

    @OneToMany(mappedBy = "product")
    private List<Comment> commentList = new ArrayList<>();
}
