package com.taewoo.silenth.controller;

import com.taewoo.silenth.service.MyPageService;
import com.taewoo.silenth.web.dto.ApiResponse;
import com.taewoo.silenth.web.dto.MyPageResponse;
import com.taewoo.silenth.web.dto.UsernameUpdateRequest;
import com.taewoo.silenth.web.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<ApiResponse<MyPageResponse>> getMyPageInfo(@AuthenticationPrincipal User user) {
        MyPageResponse myPageInfo = myPageService.getMyPageInfo(user);
        return ResponseEntity.ok(ApiResponse.onSuccessWithData(myPageInfo));
    }

    @PatchMapping("/username")
    public ResponseEntity<ApiResponse<Void>> updateUsername(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UsernameUpdateRequest request
    ) {
        myPageService.updateUsername(user.getId(), request.getUsername());
        return ResponseEntity.ok(ApiResponse.onSuccessWithMessage("닉네임이 성공적으로 변경되었습니다."));
    }

    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> updateProfileImage(
            @AuthenticationPrincipal User user,
            @RequestPart("image") MultipartFile profileImage
    ) throws IOException {
        String profileImageUrl = myPageService.updateProfileImage(user.getId(), profileImage);
        return ResponseEntity.ok(ApiResponse.onSuccessWithData(profileImageUrl));
    }
}