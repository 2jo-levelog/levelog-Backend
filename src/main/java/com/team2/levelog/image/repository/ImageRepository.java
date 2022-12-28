package com.team2.levelog.image.repository;

import com.team2.levelog.image.entity.PostImage;
import com.team2.levelog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<PostImage, Long> {
}
