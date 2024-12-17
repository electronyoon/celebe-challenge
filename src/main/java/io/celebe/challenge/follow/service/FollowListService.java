package io.celebe.challenge.follow.service;

import io.celebe.challenge.follow.mapper.FollowListMapper;
import io.celebe.challenge.model.dto.FollowListResponseDto;
import io.celebe.challenge.model.dto.UserProfileDto;
import io.celebe.challenge.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowListService {
    private final FollowListMapper followListMapper;
    private final UserMapper userMapper;

    public FollowListResponseDto getFollowers(String publicId) {
        Long userId = userMapper.selectIdByPublicId(publicId);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
        }

        List<UserProfileDto> followers = followListMapper.selectFollowers(userId);

        return FollowListResponseDto.builder()
            .users(followers)
            .totalCount(followers.size())
            .build();
    }

    public FollowListResponseDto getFollowing(String publicId) {
        Long userId = userMapper.selectIdByPublicId(publicId);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
        }

        List<UserProfileDto> following = followListMapper.selectFollowings(userId);

        return FollowListResponseDto.builder()
            .users(following)
            .totalCount(following.size())
            .build();
    }
}