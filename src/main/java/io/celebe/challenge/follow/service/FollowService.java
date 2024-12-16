package io.celebe.challenge.follow.service;

import io.celebe.challenge.follow.mapper.FollowMapper;
import io.celebe.challenge.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowMapper followMapper;
    private final UserMapper userMapper;

    @Transactional
    public void follow(String followerPublicId, String followingPublicId) {
        // 1. 사용자 ID 조회
        Long followerId = userMapper.selectIdByPublicId(followerPublicId);
        Long followingId = userMapper.selectIdByPublicId(followingPublicId);

        // 2. 유효성 검증
        if (followerId == null) {
            throw new IllegalArgumentException("현재 유저를 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followingId == null) {
            throw new IllegalArgumentException("팔로우하려는 계정을 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        // 3. 이미 활성화된 팔로우 관계가 있는지 확인
        if (followMapper.selectActiveFollow(followerId, followingId) > 0) {
            throw new IllegalStateException("이미 팔로우하고 있는 계정입니다.");
        }

        // 4. 비활성화된 것을 포함하여 팔로우 관계 확인
        if (followMapper.selectFollow(followerId, followingId) > 0) {
            // 4-1. 기존 비활성화된 팔로우 관계를 다시 활성화
            followMapper.updateFollowActive(followerId, followingId);
        } else {
            // 4-2. 새로운 팔로우 관계 생성
            followMapper.insertFollow(followerId, followingId);
        }

        // 5. 카운트 업데이트
        followMapper.updateFollowerCountIncrement(followingId);
        followMapper.updateFollowingCountIncrement(followerId);
    }

    @Transactional
    public void unfollow(String followerPublicId, String followingPublicId) {
        // 1. 사용자 ID 조회
        Long followerId = userMapper.selectIdByPublicId(followerPublicId);
        Long followingId = userMapper.selectIdByPublicId(followingPublicId);

        // 2. 유효성 검증
        if (followerId == null) {
            throw new IllegalArgumentException("현재 유저를 찾을 수 없거나 비활성화된 계정입니다.");
        }
        if (followingId == null) {
            throw new IllegalArgumentException("언팔로우하려는 계정을 찾을 수 없거나 비활성화된 계정입니다.");
        }

        // 3. 팔로우 관계 확인
        if (followMapper.selectActiveFollow(followerId, followingId) == 0) {
            throw new IllegalStateException("팔로우하고 있지 않은 계정입니다.");
        }

        // 4. 팔로우 관계 비활성화 및 카운트 업데이트
        followMapper.updateFollowInactive(followerId, followingId);
        followMapper.updateFollowerCountDecrement(followingId);
        followMapper.updateFollowingCountDecrement(followerId);
    }

}
