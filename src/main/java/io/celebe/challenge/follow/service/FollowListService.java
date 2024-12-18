package io.celebe.challenge.follow.service;

import io.celebe.challenge.follow.repository.FollowListRepository;
import io.celebe.challenge.follow.dto.FollowListDto;
import io.celebe.challenge.user.dto.UserProfileDto;
import io.celebe.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowListService {
    private final FollowListRepository followListRepository;
    private final UserRepository userRepository;

    public FollowListDto getFollowers(String publicId) {
        Long userId = userRepository.findIdByPublicId(publicId);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
        }

        List<UserProfileDto> followers = followListRepository.selectFollowers(userId);

        return FollowListDto.builder()
            .users(followers)
            .totalCount(followers.size())
            .build();
    }

    public FollowListDto getFollowing(String publicId) {
        Long userId = userRepository.findIdByPublicId(publicId);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
        }

        List<UserProfileDto> following = followListRepository.selectFollowings(userId);

        return FollowListDto.builder()
            .users(following)
            .totalCount(following.size())
            .build();
    }
}