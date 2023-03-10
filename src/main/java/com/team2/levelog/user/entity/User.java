package com.team2.levelog.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 1. 기능   : JWT 로직
// 2. 작성자 : 서혁수
@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String thumbImg;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String email, String nickname, String password, String thumbImg, UserRoleEnum role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.thumbImg = thumbImg;
        this.role = role;
    }

    public User(String email, String nickname, String password, UserRoleEnum role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public void update(String thumbImg) {
        this.thumbImg = thumbImg;
    }
}
