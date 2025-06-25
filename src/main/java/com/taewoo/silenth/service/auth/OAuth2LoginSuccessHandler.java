package com.taewoo.silenth.service.auth;

import com.taewoo.silenth.common.ErrorCode;
import com.taewoo.silenth.config.jwt.JwtProvider;
import com.taewoo.silenth.exception.BusinessException;
import com.taewoo.silenth.repository.RefreshTokenRepository;
import com.taewoo.silenth.repository.UserRepository;
import com.taewoo.silenth.web.dto.authDto.TokenResponse;
import com.taewoo.silenth.web.entity.RefreshToken;
import com.taewoo.silenth.web.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        TokenResponse tokenResponse = jwtProvider.generateToken(authentication);

        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        rt -> rt.updateToken(tokenResponse.refreshToken()),
                        () -> refreshTokenRepository.save(new RefreshToken(user, tokenResponse.refreshToken()))
                );

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5137/auth/oauth-redirect")
                .queryParam("accessToken", tokenResponse.accessToken())
                .queryParam("refreshToken", tokenResponse.refreshToken())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}
