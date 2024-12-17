package io.celebe.challenge.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
public class FollowListResponseDto {
    private List<UserProfileDto> users;
    private Integer totalCount;
}

