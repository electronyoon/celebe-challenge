package io.celebe.challenge.follow.dto;

import io.celebe.challenge.user.dto.UserProfileDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
public class FollowListDto {
    private List<UserProfileDto> users;
    private Integer totalCount;
}

