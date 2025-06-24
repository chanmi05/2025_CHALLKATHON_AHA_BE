package com.taewoo.silenth.web.dto.postDto;

import com.taewoo.silenth.web.entity.EmotionTag;
import com.taewoo.silenth.web.entity.SilentPost;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostResponse(
        Long postId,
        String content,
        String authorNickname,
        String authorProfileImageUrl,
        int echoCount,
        LocalDateTime createdAt,
        List<String> tags
) {
    public static PostResponse from(SilentPost post) {
        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getUser().getProfileImageUrl(),
                post.getEchoCount(),
                post.getCreatedAt(),
                post.getEmotionTags().stream()
                        .map(EmotionTag::getTagName)
                        .collect(Collectors.toList())
        );
    }
}
