package com.team2.levelog.image.repository;

import com.team2.levelog.image.entity.Image;
import com.team2.levelog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPost(Post post);
    List<Image> findByPostId(Long id);

}
