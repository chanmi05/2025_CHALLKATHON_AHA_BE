package com.taewoo.silenth.repository;

import com.taewoo.silenth.web.entity.SilentPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SilentPostRepository extends JpaRepository<SilentPost, Long> {
    // N + 1 문제 방지용 쿼리
    @Query("select p from SilentPost p join fetch p.user order by p.createdAt desc ")
    Page<SilentPost> findPostWithUser(Pageable pageable);

    @Query("SELECT COUNT(e) FROM Echo e WHERE e.silentPost.id = :postId")
    int countEchosByPostId(@Param("postId") Long postId);
}