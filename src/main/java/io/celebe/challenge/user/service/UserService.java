package io.celebe.challenge.user.service;

import io.celebe.challenge.common.constant.ErrorMessage;
import io.celebe.challenge.common.exception.UserNotFoundException;
import io.celebe.challenge.user.domain.User;
import io.celebe.challenge.user.dto.UserProfileDto;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfileDto getProfile(String publicId) {
        log.debug("프로필 조회 시작: publicId={}", publicId);
        User user = userRepository.findByPublicId(publicId);
        if (user == null) {
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        if (!user.getIsActive()) {
            throw new UserNotFoundException(ErrorMessage.USER_INACTIVE);
        }

        log.debug("프로필 조회 완료: user={}", user);
        return UserProfileDto.builder()
                .publicId(user.getPublicId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .thumbnailUrl(user.getThumbnailUrl())
                .followerCount(user.getFollowerCount())
                .followingCount(user.getFollowingCount())
                .postCount(user.getPostCount())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
