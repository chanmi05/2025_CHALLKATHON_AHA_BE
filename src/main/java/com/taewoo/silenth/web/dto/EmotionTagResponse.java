package com.taewoo.silenth.web.dto;

import com.taewoo.silenth.web.entity.EmotionTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmotionTagResponse {
    private Long id;
    private String tagName;
}