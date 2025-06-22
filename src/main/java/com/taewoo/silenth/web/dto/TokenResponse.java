package com.taewoo.silenth.web.dto;

public record TokenResponse(String grantType, String accessToken, String refreshToken) {}
