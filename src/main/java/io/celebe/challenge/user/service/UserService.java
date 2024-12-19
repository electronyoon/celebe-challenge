package io.celebe.challenge.user.service;

import io.celebe.challenge.user.domain.User;
import io.celebe.challenge.user.dto.UserProfileDto;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfileDto getProfile(String publicId) {
        log.debug("프로필 조회 시작: publicId={}", publicId);
        User user = userRepository.findByPublicId(publicId);
        if (user == null) {
            log.warn("존재하지 않는 유저 조회 시도: publicId={}", publicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
        }
        if (!user.getIsActive()) {
            log.warn("비활성화된 계정 조회 시도: publicId={}", publicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "비활성화된 계정입니다.");
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
