package io.celebe.challenge.follow.service;

import io.celebe.challenge.follow.repository.FollowRepository;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(String followerPublicId, String followingPublicId) {
        // 1. 사용자 ID 조회
        Long followerId = userRepository.findIdByPublicId(followerPublicId);
        Long followingId = userRepository.findIdByPublicId(followingPublicId);

        // 2. 유효성 검증
        if (followerId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "현재 유저를 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followingId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "팔로우하려는 계정을 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followerId.equals(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우할 수 없습니다.");
        }

        // 3. 이미 활성화된 팔로우 관계가 있는지 확인
        if (followRepository.findActiveFollow(followerId, followingId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 팔로우하고 있는 계정입니다.");
        }

        // 4. 비활성화된 것을 포함하여 팔로우 관계 확인
        if (followRepository.findFollow(followerId, followingId) > 0) {
            // 4-1. 기존 비활성화된 팔로우 관계를 다시 활성화
            followRepository.updateInactive(followerId, followingId);
        } else {
            // 4-2. 새로운 팔로우 관계 생성
            followRepository.save(followerId, followingId);
        }

        // 5. 카운트 업데이트
        followRepository.updateFollowerCountIncrement(followingId);
        followRepository.updateFollowingCountIncrement(followerId);
    }

    @Transactional
    public void unfollow(String followerPublicId, String followingPublicId) {
        // 1. 사용자 ID 조회
        Long followerId = userRepository.findIdByPublicId(followerPublicId);
        Long followingId = userRepository.findIdByPublicId(followingPublicId);

        // 2. 유효성 검증
        if (followerId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "현재 유저를 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followingId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "언팔로우하려는 계정을 찾을 수 없거나 비활성화된 계정입니다.");
        }

        // 3. 팔로우 관계 확인
        if (followRepository.findActiveFollow(followerId, followingId) == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "팔로우하고 있지 않은 계정입니다.");
        }

        // 4. 팔로우 관계 비활성화 및 카운트 업데이트
        followRepository.updateActive(followerId, followingId);
        followRepository.updateFollowerCountDecrement(followingId);
        followRepository.updateFollowingCountDecrement(followerId);
    }

}
