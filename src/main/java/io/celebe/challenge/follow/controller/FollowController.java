package io.celebe.challenge.follow.controller;

import io.celebe.challenge.common.response.CelebeApiResponse;
import io.celebe.challenge.common.response.ErrorResponse;
import io.celebe.challenge.follow.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "2. 팔로우 API", description = "팔로우/언팔로우 API")
@RequestMapping("/api/profiles/{publicId}")
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "2-1. 팔로우", description = "특정 사용자를 팔로우합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "팔로우 성공", content = @Content(schema = @Schema(implementation = CelebeApiResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (자기 자신을 팔로우)", content = @Content),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "이미 팔로우한 사용자", content = @Content)
    })
    @PostMapping("/follow")
    public ResponseEntity<Void> follow(
        @Parameter(description = "요청자의 식별자. 6자리의 영문 소문자와 숫자 조합. (HTTP 헤더로 제공된다고 가정)",
                   schema = @Schema(pattern = "^[a-z0-9]{6}$",
                   example = "000001")) @RequestHeader("CUSTOM-AUTH-ID") String followerPublicId,
        @Parameter(description = "팔로우할 사용자의 식별자. 6자리의 영문 소문자와 숫자 조합",
                   schema = @Schema(pattern = "^[a-z0-9]{6}$",
                   example = "000003")) @PathVariable("publicId") String followingPublicId
    ) {
        log.debug("팔로우 요청: follower={}, following={}", followerPublicId, followingPublicId);
        followService.follow(followerPublicId, followingPublicId);
        log.debug("팔로우 완료: follower={}, following={}", followerPublicId, followingPublicId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "2-2. 언팔로우", description = "특정 사용자 팔로우를 취소합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "언팔로우 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CelebeApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "팔로우 상태가 아닌 사용자", content = @Content)
    })
    @DeleteMapping("/follow")
    public ResponseEntity<Void> unfollow(
        @Parameter(description = "요청자의 식별자. 6자리의 영문 소문자와 숫자 조합. (HTTP 헤더로 제공된다고 가정)",
                   schema = @Schema(pattern = "^[a-z0-9]{6}$",
                   example = "000001")) @RequestHeader("CUSTOM-AUTH-ID") String followerPublicId,
        @Parameter(description = "언팔로우할 사용자의 식별자. 6자리의 영문 소문자와 숫자 조합",
                   schema = @Schema(pattern = "^[a-z0-9]{6}$",
                   example = "000002")) @PathVariable("publicId") String followingPublicId
    ) {
        log.debug("언팔로우 요청: follower={}, following={}", followerPublicId, followingPublicId);
        followService.unfollow(followerPublicId, followingPublicId);
        log.debug("언팔로우 완료: follower={}, following={}", followerPublicId, followingPublicId);
        return ResponseEntity.ok().build();
    }

}
