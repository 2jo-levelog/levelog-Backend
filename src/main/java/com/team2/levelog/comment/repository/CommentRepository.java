package com.team2.levelog.comment.repository;

import com.team2.levelog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// 1. 기능 : 댓글 Repository
// 2. 작성자 : 조소영
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
