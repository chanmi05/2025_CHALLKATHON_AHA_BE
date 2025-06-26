package com.taewoo.silenth.repository;

import com.taewoo.silenth.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLoginId(String loginId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String username);
    boolean existsByLoginId(String loginId);
}
