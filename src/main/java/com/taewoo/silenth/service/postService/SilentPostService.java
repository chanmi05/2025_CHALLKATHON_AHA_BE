package com.taewoo.silenth.service.postService;

import com.taewoo.silenth.web.dto.postDto.PostResponse;
import com.taewoo.silenth.web.dto.postDto.SilentPostCreateRequest;
import com.taewoo.silenth.web.dto.postDto.SilentPostCreateResponse;
import com.taewoo.silenth.web.entity.EmotionTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SilentPostService {
    SilentPostCreateResponse createPost(Long userId, SilentPostCreateRequest request);
    Page<PostResponse> getPostFeed(Pageable pageable);
    void giveArchivingConsent(Long userId, Long postId);
    Page<PostResponse> getMyPosts(Long userId, Pageable pageable);
    List<EmotionTag> analyzeAndCreateTags(String content);

    // 👇 아래 메서드 시그니처를 추가합니다.
    List<PostResponse> getPostsByTagName(String tagName);
}
