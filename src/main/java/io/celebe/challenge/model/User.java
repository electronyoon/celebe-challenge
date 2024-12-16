package io.celebe.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonIgnore
    private boolean isActive;
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
}

