package io.celebe.challenge.follow.service;

import io.celebe.challenge.common.constant.ErrorMessage;
import io.celebe.challenge.common.exception.UserNotFoundException;
import io.celebe.challenge.follow.dto.FollowListDto;
import io.celebe.challenge.follow.repository.FollowListRepository;
import io.celebe.challenge.user.dto.UserProfileDto;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowListService {
    private final FollowListRepository followListRepository;
    private final UserRepository userRepository;

    public FollowListDto getFollowers(String publicId) {
        log.debug("팔로워 목록 조회 시작: publicId={}", publicId);
        Long userId = userRepository.findIdByPublicId(publicId);
        if (userId == null) {
            throw new UserNotFoundException(ErrorMessage.TARGET_USER_NOT_FOUND);
        }

        List<UserProfileDto> followers = followListRepository.findFollowers(userId);
        log.debug("팔로워 목록 조회 완료: publicId={}, userId={}, count={}, followerIds={}",
            publicId,
            userId,
            followers.size(),
            followers.stream()
                    .map(UserProfileDto::getPublicId)
                    .collect(Collectors.joining(", "))
        );

        return FollowListDto.builder()
            .users(followers)
            .totalCount(followers.size())
            .build();
    }

    public FollowListDto getFollowings(String publicId) {
        log.debug("팔로잉 목록 조회 시작: publicId={}", publicId);
        Long userId = userRepository.findIdByPublicId(publicId);
        if (userId == null) {
            throw new UserNotFoundException(ErrorMessage.TARGET_USER_NOT_FOUND);
        }

        List<UserProfileDto> followings = followListRepository.findFollowings(userId);
        log.debug("팔로잉 목록 조회 완료: publicId={}, userId={}, count={}, followingIds={}",
            publicId,
            userId,
            followings.size(),
            followings.stream()
                    .map(UserProfileDto::getPublicId)
                    .collect(Collectors.joining(", "))
        );

        return FollowListDto.builder()
            .users(followings)
            .totalCount(followings.size())
            .build();
    }
}