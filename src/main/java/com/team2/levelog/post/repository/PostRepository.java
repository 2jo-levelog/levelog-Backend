package com.team2.levelog.post.repository;

import com.team2.levelog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();   // 수정된 시간 기준
    List<Post> findAllByOrderByCreatedAtDesc();    // 생성된 시간 기준
    List<Post> findAllByUserId(Long id);
}
