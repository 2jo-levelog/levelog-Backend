package com.team2.levelog.post.repository;

import com.team2.levelog.post.entity.Likes;
import com.team2.levelog.post.entity.Post;
import com.team2.levelog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findAllByUserId(Long id);
    @Transactional
    void deleteByPostAndUser(Post post, User user);

    Optional<Likes> findByPostAndUser(Post post, User user)  ;

    void deleteByUserId(Long userId);
}
