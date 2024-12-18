package io.celebe.challenge.follow.repository;

import io.celebe.challenge.user.dto.UserProfileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowListRepository {
    List<UserProfileDto> selectFollowers(@Param("userId") Long userId);

    List<UserProfileDto> selectFollowings(@Param("userId") Long userId);
}
