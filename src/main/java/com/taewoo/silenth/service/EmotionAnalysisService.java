package com.taewoo.silenth.service;

import com.taewoo.silenth.external.gemini.GeminiClient;
import com.taewoo.silenth.repository.EmotionTagRepository;
import com.taewoo.silenth.web.dto.EmotionTagResponse;
import com.taewoo.silenth.web.entity.EmotionTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmotionAnalysisService {

    private final GeminiClient geminiClient;
    private final EmotionTagRepository emotionTagRepository;

    @Transactional
    public List<EmotionTagResponse> analyze(String content) {
        String prompt = """
                다음 텍스트에서 느껴지는 감정을 간결한 키워드 3~5개로 추출해줘.
                감정 예시 : 슬픔, 기쁨, 피곤함, 안정감, 두려움, 기대감 등등
                
                텍스트 : "%s"
                감정 키워드:
                """.formatted(content);

        List<String> tagNames = geminiClient.getEmotionTags(prompt);

        return tagNames.stream().map(tagName -> {
            Optional<EmotionTag> existing = emotionTagRepository.findByTagName(tagName);
            EmotionTag tag = existing.orElseGet(() -> emotionTagRepository.save(new EmotionTag(tagName)));
            return new EmotionTagResponse(tag.getId(), tag.getTagName());
        }).collect(Collectors.toList());
    }
}
