package io.celebe.challenge.follow.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowRepository {
    // 팔로우 관계 조회
    Integer findFollow(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    // 활성화된 팔로우 관계만 조회
    Integer findActiveFollow(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    // 팔로우 관계 생성
    void save(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    // 팔로우 관계 비활성화 (언팔로우)
    void updateActive(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    // 비활성화된 팔로우 관계를 다시 활성화
    void updateInactive(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    // 팔로워/팔로잉 카운트 업데이트
    void updateFollowerCountIncrement(@Param("userId") Long userId);
    void updateFollowerCountDecrement(@Param("userId") Long userId);
    void updateFollowingCountIncrement(@Param("userId") Long userId);
    void updateFollowingCountDecrement(@Param("userId") Long userId);
}