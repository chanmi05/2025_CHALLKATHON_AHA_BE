package com.taewoo.silenth.service.echo;

import com.taewoo.silenth.common.ErrorCode;
import com.taewoo.silenth.exception.BusinessException;
import com.taewoo.silenth.repository.EchoRepository;
import com.taewoo.silenth.repository.SilentPostRepository;
import com.taewoo.silenth.repository.UserRepository;
import com.taewoo.silenth.web.dto.postDto.EchoResponse;
import com.taewoo.silenth.web.entity.Echo;
import com.taewoo.silenth.web.entity.SilentPost;
import com.taewoo.silenth.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EchoServiceImpl implements EchoService {

    private final UserRepository userRepository;
    private final SilentPostRepository silentPostRepository;
    private final EchoRepository echoRepository;

    @Override
    @Transactional
    public EchoResponse toggleEcho(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        SilentPost silentPost = silentPostRepository.findByIdWithEchos(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        Optional<Echo> existingEcho = silentPost.getEchos().stream()
                .filter(e -> e.getUser().getId().equals(userId))
                .findFirst();

        if (existingEcho.isPresent()) {
            silentPost.getEchos().remove(existingEcho.get());
            return new EchoResponse(silentPost.getEchoCount(), false);
        } else {
            Echo newEcho = Echo.builder().user(user).silentPost(silentPost).build();
            silentPost.getEchos().add(newEcho);
            return new EchoResponse(silentPost.getEchoCount(), true);
        }
    }
}
