package com.taewoo.silenth.web.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SilentPostCreateResponse {
    private Long postId;
    private String message;
    private List<String> emotionTags;   // 태그 이름 리스트 추가
}