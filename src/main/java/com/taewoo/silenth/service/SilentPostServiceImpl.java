package com.taewoo.silenth.service;

import com.taewoo.silenth.common.ErrorCode;
import com.taewoo.silenth.exception.BusinessException;
import com.taewoo.silenth.repository.EmotionTagRepository;
import com.taewoo.silenth.repository.SilentPostRepository;
import com.taewoo.silenth.repository.UserRepository;
import com.taewoo.silenth.web.dto.SilentPostCreateRequest;
import com.taewoo.silenth.web.dto.SilentPostCreateResponse;
import com.taewoo.silenth.web.entity.EmotionTag;
import com.taewoo.silenth.web.entity.SilentPost;
import com.taewoo.silenth.web.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SilentPostServiceImpl implements SilentPostService {

    private final UserRepository userRepository;
    private final SilentPostRepository silentPostRepository;
    private final EmotionTagRepository emotionTagRepository;

    @Transactional
    @Override
    public SilentPostCreateResponse createPost(Long userId, SilentPostCreateRequest request) {
        // 1. 사용자 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 감정 태그 유효성 검사
        if (request.getEmotionTagIds() == null || request.getEmotionTagIds().isEmpty()){
           throw new BusinessException(ErrorCode.EMPTY_EMOTION_TAGS);
        }

        // 3. 감정 태그 조회
        List<EmotionTag> emotionTags =  emotionTagRepository.findByIdIn(request.getEmotionTagIds());

        if(emotionTags.size() != request.getEmotionTagIds().size()){
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        // 4. 게시글 생성 및 연관관계 설정
        SilentPost post = new SilentPost();
        post.setUser(user);
        post.setEmotionTags(emotionTags);
        post.setContent(request.getContent());

        SilentPost saved = silentPostRepository.save(post);

        // 5. response 변환
        return new SilentPostCreateResponse(saved.getId(), "작성 완료!");
    }
}