package com.taewoo.silenth.repository;

import com.taewoo.silenth.web.entity.EmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmotionTagRepository extends JpaRepository<EmotionTag, Long> {
    List<EmotionTag> findByIdIn(List<Long> ids);

    Optional<EmotionTag> findByTagName(String tagName);
}