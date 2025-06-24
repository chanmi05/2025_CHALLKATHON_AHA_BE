package com.taewoo.silenth.repository;

import com.taewoo.silenth.web.entity.SilentPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SilentPostRepository extends JpaRepository<SilentPost, Long> {
}