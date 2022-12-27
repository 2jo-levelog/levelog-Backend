package com.team2.levelog.post.repository;

import com.team2.levelog.post.entity.Likes;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Transactional
    void deleteByPostAndUser(Post post, User user);                    // Post와 User 정보로 DB에서 좋아요 검색 후 삭제

    Optional<Likes> findByPostAndUser(Post post, User user);           // Post와 User 정보로 DB에서 좋아요 검색 후 Optional로 감싸서 반환
}
