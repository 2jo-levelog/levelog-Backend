package com.team2.levelog.image.repository;

import com.team2.levelog.image.repository.entity.PostImage;
import com.team2.levelog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// 1. 기능   : 이미지 첨부 Repository
// 2. 작성자 : 박소연
public interface ImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost(Post post);
}
