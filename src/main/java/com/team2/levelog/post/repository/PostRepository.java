package com.team2.levelog.post.repository;

import com.team2.levelog.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// 1. 기능      :   게시글 Repository
// 2. 작성자    :   홍윤재
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByNickname(String userNickname, Pageable pageable);       // 유저 Nickname로 검색해서 Page 형식으로 감싼 뒤 반환

    void deleteByUserNickname(String username);
}
