package com.taewoo.silenth.service.auth;

import com.taewoo.silenth.common.Role;
import com.taewoo.silenth.config.UserPrincipal;
import com.taewoo.silenth.repository.UserRepository;
import com.taewoo.silenth.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String nickname = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // DB에 없는 사용자면, 우리 시스템에 맞게 자동 회원가입
                    String loginId = "google_" + oAuth2User.getAttribute("sub"); // 구글 고유 ID로 로그인 ID 생성
                    return userRepository.save(User.builder()
                            .email(email)
                            .loginId(loginId)
                            .nickname(nickname)
                            .password("OAUTH2_USER") // 소셜 로그인 사용자는 비밀번호가 필요 없음
                            .role(Role.USER)
                            .build());
                });

        return new UserPrincipal(user, oAuth2User.getAttributes());
    }
}
