package com.team2.levelog.image.entity;

import com.team2.levelog.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class PostImage {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String imagePath; // 파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public PostImage(Post post, String imagePath) {
        this.imagePath = imagePath;
        this.post = post;
        post.getPostImageList().add(this);
    }

    public void update(Post post) {
        this.post = post;
        post.getPostImageList().add(this);
    }
}
