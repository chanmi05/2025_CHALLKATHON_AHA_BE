package com.taewoo.silenth.converter;

import com.taewoo.silenth.web.dto.postDto.SilentPostCreateResponse;
import com.taewoo.silenth.web.entity.SilentPost;

import java.util.List;

public class SilentPostConverter {

    public static SilentPostCreateResponse toCreateResponse(SilentPost post) {
        List<String> tagNames = post.getEmotionTags().stream()
                .map(e -> e.getEmotionTag().getTagName())
                .toList();

        return SilentPostCreateResponse.builder()
                .postId(post.getId())
                .message("작성 완료")
                .emotionTags(tagNames)
                .build();
    }
}