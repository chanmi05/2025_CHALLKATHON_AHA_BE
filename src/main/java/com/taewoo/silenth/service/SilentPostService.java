package com.taewoo.silenth.service;

import com.taewoo.silenth.web.dto.SilentPostCreateRequest;
import com.taewoo.silenth.web.dto.SilentPostCreateResponse;

public interface SilentPostService {
    SilentPostCreateResponse createPost(Long userId, SilentPostCreateRequest request);
}