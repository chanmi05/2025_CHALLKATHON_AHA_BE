package com.taewoo.silenth.controller;

import com.taewoo.silenth.service.EmotionAnalysisService;
import com.taewoo.silenth.web.dto.EmotionAnalysisRequest;
import com.taewoo.silenth.web.dto.EmotionTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/emotions")
@RequiredArgsConstructor
public class EmotionAnalysisController {

    private final EmotionAnalysisService emotionAnalysisService;

    @PostMapping("/analyze")
    public ResponseEntity<List<EmotionTagResponse>> analyzeEmotion(@RequestBody EmotionAnalysisRequest request) {
        List<EmotionTagResponse> tags = emotionAnalysisService.analyze(request.content());
        return ResponseEntity.ok(tags);
    }
}