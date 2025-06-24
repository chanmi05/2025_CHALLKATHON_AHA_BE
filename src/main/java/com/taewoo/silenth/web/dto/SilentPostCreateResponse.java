package com.taewoo.silenth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SilentPostCreateResponse {
    private Long postId;
    private String message;
}