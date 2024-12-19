package io.celebe.challenge.user.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class User {
    private Long id;
    private String publicId;
    private String email;
    private String nickname;
    private String name;
    private String bio;
    private String link1;
    private String link2;
    private String thumbnailUrl;
    private Integer followerCount;
    private Integer followingCount;
    private Integer postCount;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

