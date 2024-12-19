package io.celebe.challenge.follow.service;

import io.celebe.challenge.common.constant.ErrorMessage;
import io.celebe.challenge.common.exception.FollowException;
import io.celebe.challenge.common.exception.UserNotFoundException;
import io.celebe.challenge.follow.repository.FollowRepository;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        if (followingId == null) {
            throw new UserNotFoundException(ErrorMessage.TARGET_USER_NOT_FOUND);
        }
        if (followerId.equals(followingId)) {
            throw new FollowException(ErrorMessage.FOLLOW_SELF, HttpStatus.BAD_REQUEST);
        }

        if (followRepository.findActiveFollow(followerId, followingId) > 0) {
            throw new FollowException(ErrorMessage.ALREADY_FOLLOWING, HttpStatus.CONFLICT);
        }

        if (followRepository.findFollow(followerId, followingId) > 0) {
            followRepository.updateInactive(followerId, followingId);
        } else {
            followRepository.save(followerId, followingId);
        }
        log.debug("팔로우 관계 생성 완료: follower={}, following={}", followerPublicId, followingPublicId);

        followRepository.save(followerId, followingId);
        followRepository.updateFollowerCountIncrement(followingId);
        followRepository.updateFollowingCountIncrement(followerId);
    }

    @Transactional
    public void unfollow(String followerPublicId, String followingPublicId) {
        log.debug("언팔로우 처리 시작: follower={}, following={}", followerPublicId, followingPublicId);
        Long followerId = userRepository.findIdByPublicId(followerPublicId);
        Long followingId = userRepository.findIdByPublicId(followingPublicId);

        if (followerId == null) {
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        if (followingId == null) {
            throw new UserNotFoundException(ErrorMessage.TARGET_USER_NOT_FOUND);
        }

        if (followRepository.findActiveFollow(followerId, followingId) == 0) {
            throw new FollowException(ErrorMessage.NOT_FOLLOWING, HttpStatus.CONFLICT);
        }
        log.debug("언팔로우 처리 완료: follower={}, following={}", followerPublicId, followingPublicId);

        followRepository.updateActive(followerId, followingId);
        followRepository.updateFollowerCountDecrement(followingId);
        followRepository.updateFollowingCountDecrement(followerId);
    }

}
