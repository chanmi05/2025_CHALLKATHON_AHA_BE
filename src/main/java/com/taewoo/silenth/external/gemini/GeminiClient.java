// src/main/java/com/taewoo/silenth/external/gemini/GeminiClient.java
package com.taewoo.silenth.external.gemini;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final RestTemplate restTemplate;

    @Value("${gemini.apiKey}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    // 공통 베이스 URL
    private static final String BASE_URL =
            "https://generativelanguage.googleapis.com/v1beta/models";

    public List<String> getEmotionTags(String prompt) {
        String url = String.format("%s/%s:generateContent?key=%s",
                BASE_URL, model, apiKey);

        // 1) 요청 바디 구성
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        // 2) 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);

        // 3) 호출
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        log.debug("🔍 Gemini raw response body: {}", response.getBody());

        try {
            // 4) 본문 파싱
            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("candidates"))
                throw new RuntimeException("Gemini 응답에 candidates가 없습니다.");

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) body.get("candidates");
            if (candidates.isEmpty())
                throw new RuntimeException("Gemini 응답이 비어 있습니다.");

            @SuppressWarnings("unchecked")
            Map<String, Object> contentMap =
                    (Map<String, Object>) candidates.get(0).get("content");
            if (contentMap == null || !contentMap.containsKey("parts"))
                throw new RuntimeException("Gemini 콘텐츠 파싱 실패: parts가 없습니다.");

            @SuppressWarnings("unchecked")
            List<Map<String, String>> parts =
                    (List<Map<String, String>>) contentMap.get("parts");
            if (parts.isEmpty() || !parts.get(0).containsKey("text"))
                throw new RuntimeException("Gemini 콘텐츠 파싱 실패: text가 없습니다.");

            String resultText = parts.get(0).get("text");
            if (resultText == null || resultText.isBlank())
                return List.of();

            // 5) 결과 분리
            return List.of(resultText.split("[,\\n]")).stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

        } catch (Exception e) {
            log.error("❌ Gemini 응답 파싱 실패", e);
            throw new RuntimeException("Gemini 응답 파싱 실패", e);
        }
    }
}