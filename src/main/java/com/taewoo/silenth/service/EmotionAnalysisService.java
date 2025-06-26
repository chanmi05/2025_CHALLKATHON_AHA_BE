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
                다음 텍스트에서 느껴지는 감정을 3~5개의 간결한 키워드로 쉼표로 구분해 출력해줘.
                예시 : 슬픔, 기쁨, 피곤함, 안정감, 기대감
                
                텍스트 : "%s"
                """.formatted(content);

        List<String> tagNames = geminiClient.getEmotionTags(prompt);

        return tagNames.stream().map(tagName -> {
            Optional<EmotionTag> existing = emotionTagRepository.findByTagName(tagName);
            EmotionTag tag = existing.orElseGet(() -> emotionTagRepository.save(new EmotionTag(tagName)));
            return new EmotionTagResponse(tag.getId(), tag.getTagName());
        }).collect(Collectors.toList());
    }
}
