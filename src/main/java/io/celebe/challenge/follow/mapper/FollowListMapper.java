package io.celebe.challenge.follow.mapper;

import io.celebe.challenge.model.dto.UserProfileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowListMapper {
    List<UserProfileDto> selectFollowers(@Param("userId") Long userId);

    List<UserProfileDto> selectFollowings(@Param("userId") Long userId);
}
