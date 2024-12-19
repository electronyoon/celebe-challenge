package io.celebe.challenge.follow.dto;

import io.celebe.challenge.user.dto.UserProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@Schema(description = "팔로워/팔로잉 목록 정보")
public class FollowListDto {
    @Schema(description = "사용자 목록")
    private List<UserProfileDto> users;

    @Schema(description = "총 사용자 수", example = "50")
    private Integer totalCount;
}

