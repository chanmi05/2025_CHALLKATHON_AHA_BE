package com.taewoo.silenth.service.postService;

import com.taewoo.silenth.common.ErrorCode;
import com.taewoo.silenth.converter.SilentPostConverter;
import com.taewoo.silenth.exception.BusinessException;
import com.taewoo.silenth.external.gemini.GeminiClient;
import com.taewoo.silenth.repository.EmotionTagRepository;
import com.taewoo.silenth.repository.SilentPostRepository;
import com.taewoo.silenth.repository.UserRepository;
import com.taewoo.silenth.web.dto.postDto.PostResponse;
import com.taewoo.silenth.web.dto.postDto.SilentPostCreateRequest;
import com.taewoo.silenth.web.dto.postDto.SilentPostCreateResponse;
import com.taewoo.silenth.web.entity.EmotionTag;
import com.taewoo.silenth.web.entity.SilentPost;
import com.taewoo.silenth.web.entity.SilentPostEmotionTag;
import com.taewoo.silenth.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 클래스 레벨에 readOnly 추가
public class SilentPostServiceImpl implements SilentPostService {

    private final UserRepository userRepository;
    private final SilentPostRepository silentPostRepository;
    private final EmotionTagRepository emotionTagRepository;
    private final GeminiClient geminiClient;

    @Override
    @Transactional
    public List<EmotionTag> analyzeAndCreateTags(String content){
        // ... (기존 로직 유지)
        List<String> emotionTags = geminiClient.getEmotionTags(content);
        List<EmotionTag> tags = new ArrayList<>();
        for(String emotionTag : emotionTags){
            EmotionTag tag = emotionTagRepository.findByTagName(emotionTag)
                    .orElseGet(() -> emotionTagRepository.save(new EmotionTag(emotionTag)));
            tags.add(tag);
        }
        return tags;
    }

    @Override
    @Transactional
    public SilentPostCreateResponse createPost(Long userId, SilentPostCreateRequest request) {
        // ... (기존 로직 유지)
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        if (request.getEmotionTagIds() == null || request.getEmotionTagIds().isEmpty()){
            throw new BusinessException(ErrorCode.EMPTY_EMOTION_TAGS);
        }
        List<EmotionTag> emotionTags =  emotionTagRepository.findByIdIn(request.getEmotionTagIds());
        if(emotionTags.size() != request.getEmotionTagIds().size()){
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
        SilentPost post = new SilentPost();
        post.setUser(user);
        post.setContent(request.getContent());
        for(EmotionTag tag : emotionTags){
            SilentPostEmotionTag link = new SilentPostEmotionTag();
            link.setSilentPost(post);
            link.setEmotionTag(tag);
            post.addEmotionTag(link);
        }
        boolean isAnonymous = request.getIsAnonymous() != null ? request.getIsAnonymous() : false;
        post.setAnonymous(isAnonymous);
        SilentPost saved = silentPostRepository.save(post);
        return SilentPostConverter.toCreateResponse(saved);
    }

    @Override
    public Page<PostResponse> getPostFeed(Pageable pageable) {
        return silentPostRepository.findPostWithUser(pageable).map(PostResponse::from);
    }

    @Override
    @Transactional
    public void giveArchivingConsent(Long userId, Long postId) {
        // ... (기존 로직 유지)
        SilentPost post = silentPostRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_POST_ACCESS);
        }

        post.giveConsent();
    }

    @Override
    public Page<PostResponse> getMyPosts(Long userId, Pageable pageable) {
        return silentPostRepository.findByUserIdWithUser(userId, pageable)
                .map(PostResponse::from);
    }

    // 👇 아래 메서드를 구현합니다.
    @Override
    public List<PostResponse> getPostsByTagName(String tagName) {
        List<SilentPost> posts = silentPostRepository.findByEmotionTagName(tagName);
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
}
