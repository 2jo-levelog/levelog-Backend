package com.team2.levelog.post.repository;

import com.team2.levelog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Post, Long> {
}
