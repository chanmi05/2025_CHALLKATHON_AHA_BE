package com.taewoo.silenth.service.postService;

import com.taewoo.silenth.web.dto.postDto.PostResponse;
import com.taewoo.silenth.web.dto.postDto.SilentPostCreateRequest;
import com.taewoo.silenth.web.dto.postDto.SilentPostCreateResponse;
import com.taewoo.silenth.web.entity.EmotionTag;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SilentPostService {
    SilentPostCreateResponse createPost(Long userId, SilentPostCreateRequest request);
    Page<PostResponse> getPostFeed(Pageable pageable);
    void giveArchivingConsent(Long userId, Long postId);
    List<EmotionTag> analyzeAndCreateTags(String content);
}