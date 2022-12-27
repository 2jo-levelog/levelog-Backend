package com.team2.levelog.global.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserNickname(String nickname);

    void deleteByUserNickname(String nickname);

    boolean existsByRefreshToken(String rsToken);
}
