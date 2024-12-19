package io.celebe.challenge.follow.service;

import io.celebe.challenge.follow.repository.FollowRepository;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(String followerPublicId, String followingPublicId) {
        log.debug("팔로우 처리 시작: follower={}, following={}", followerPublicId, followingPublicId);
        Long followerId = userRepository.findIdByPublicId(followerPublicId);
        Long followingId = userRepository.findIdByPublicId(followingPublicId);

        if (followerId == null) {
            log.warn("존재하지 않는 팔로워: publicId={}", followerPublicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "현재 유저를 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followingId == null) {
            log.warn("존재하지 않는 팔로우 대상: publicId={}", followingPublicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "팔로우하려는 계정을 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followerId.equals(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우할 수 없습니다.");
        }

        if (followRepository.findActiveFollow(followerId, followingId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 팔로우하고 있는 계정입니다.");
        }

        if (followRepository.findFollow(followerId, followingId) > 0) {
            followRepository.updateInactive(followerId, followingId);
        } else {
            followRepository.save(followerId, followingId);
        }
        log.debug("팔로우 관계 생성 완료: follower={}, following={}", followerPublicId, followingPublicId);

        followRepository.updateFollowerCountIncrement(followingId);
        followRepository.updateFollowingCountIncrement(followerId);
    }

    @Transactional
    public void unfollow(String followerPublicId, String followingPublicId) {
        log.debug("언팔로우 처리 시작: follower={}, following={}", followerPublicId, followingPublicId);
        Long followerId = userRepository.findIdByPublicId(followerPublicId);
        Long followingId = userRepository.findIdByPublicId(followingPublicId);

        if (followerId == null) {
            log.warn("존재하지 않는 팔로워: publicId={}", followerPublicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "현재 유저를 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followingId == null) {
            log.warn("존재하지 않는 언팔로우 대상: publicId={}", followingPublicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "언팔로우하려는 계정을 찾을 수 없거나 비활성화된 계정입니다.");
        }

        if (followRepository.findActiveFollow(followerId, followingId) == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "팔로우하고 있지 않은 계정입니다.");
        }
        log.debug("언팔로우 처리 완료: follower={}, following={}", followerPublicId, followingPublicId);

        followRepository.updateActive(followerId, followingId);
        followRepository.updateFollowerCountDecrement(followingId);
        followRepository.updateFollowingCountDecrement(followerId);
    }

}
