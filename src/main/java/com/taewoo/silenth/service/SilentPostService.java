package com.taewoo.silenth.service;

import com.taewoo.silenth.web.dto.PostResponse;
import com.taewoo.silenth.web.dto.SilentPostCreateRequest;
import com.taewoo.silenth.web.dto.SilentPostCreateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SilentPostService {
    SilentPostCreateResponse createPost(Long userId, SilentPostCreateRequest request);
    Page<PostResponse> getPostFeed(Pageable pageable);
}