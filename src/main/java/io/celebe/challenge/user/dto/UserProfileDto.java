package io.celebe.challenge.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "사용자 프로필 정보")
public class UserProfileDto {
    @Schema(description = "6자리 고유 식별자", example = "abc123")
    private String publicId;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "닉네임", example = "user")
    private String nickname;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "썸네일 URL", example = "https://example.com/thumbnails/user1.jpg")
    private String thumbnailUrl;

    @Schema(description = "팔로워 수", example = "1000")
    private Integer followerCount;

    @Schema(description = "팔로잉 수", example = "500")
    private Integer followingCount;

    @Schema(description = "게시물 수", example = "25")
    private Integer postCount;

    @Schema(description = "계정 생성일시", example = "2024-12-19T23:59:59")
    private LocalDateTime createdAt;
}

