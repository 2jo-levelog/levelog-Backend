package com.team2.levelog.comment.repository;

import com.team2.levelog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

// 1. 기능 : 댓글 Repository
// 2. 작성자 : 조소영
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
