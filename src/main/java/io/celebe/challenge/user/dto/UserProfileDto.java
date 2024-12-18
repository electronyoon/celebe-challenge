package io.celebe.challenge.user.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserProfileDto {
    private String publicId;
    private String nickname;
    private String name;
    private String thumbnailUrl;
    private Boolean isFollowedBack;
}
