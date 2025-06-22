package com.taewoo.silenth.web.dto;

import com.taewoo.silenth.web.entity.User;
import lombok.Getter;

@Getter
public class MyPageResponse {
    private final String email;
    private final String username;
    private final String profileImageUrl;

    public MyPageResponse(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
