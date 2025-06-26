package com.taewoo.silenth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EmotionAnalysisResponse {
    private List<String> emotionTags;
}
