package io.celebe.challenge.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserProfileDto {
    private String publicId;
    private String email;
    private String nickname;
    private String name;
    private String thumbnailUrl;
    private Integer followerCount;
    private Integer followingCount;
    private Integer postCount;
    private LocalDateTime createdAt;
}
