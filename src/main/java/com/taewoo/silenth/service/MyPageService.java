package com.taewoo.silenth.service;

import com.taewoo.silenth.common.ErrorCode;
import com.taewoo.silenth.exception.BusinessException;
import com.taewoo.silenth.repository.UserRepository;
import com.taewoo.silenth.util.FileUploader;
import com.taewoo.silenth.web.dto.MyPageResponse;
import com.taewoo.silenth.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final FileUploader fileUploader;

    @Transactional(readOnly = true)
    public MyPageResponse getMyPageInfo(User user) {
        return new MyPageResponse(user);
    }

    public void updateUsername(Long userId, String newUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getUsername().equals(newUsername)) {
            throw new BusinessException(ErrorCode.SAME_USERNAME);
        }

        if (userRepository.existsByUsername(newUsername)) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        user.updateUsername(newUsername);
    }

    public String updateProfileImage(Long userId, MultipartFile profileImage) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String profileImageUrl = fileUploader.upload(profileImage, "profile");
        user.updateProfilePicture(profileImageUrl);
        return profileImageUrl;
    }
}
