package com.team2.levelog.user.repository;

import com.team2.levelog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// 1. 기능   : 유저 Repository
// 2. 작성자 : 서혁수
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
