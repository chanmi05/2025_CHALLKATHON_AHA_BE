package com.taewoo.silenth.repository;

import com.taewoo.silenth.web.entity.RefreshToken;
import com.taewoo.silenth.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserId(Long userId);

    String token(String token);
}
