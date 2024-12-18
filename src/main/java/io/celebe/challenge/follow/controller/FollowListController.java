package io.celebe.challenge.follow.controller;

import io.celebe.challenge.common.response.CelebeApiResponse;
import io.celebe.challenge.common.response.ErrorResponse;
import io.celebe.challenge.follow.dto.FollowListDto;
import io.celebe.challenge.follow.service.FollowListService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "3. 팔로우 리스트 API", description = "팔로워/팔로잉 목록 조회 API")
@RequestMapping("/api/profiles/{publicId}")
public class FollowListController {
    private final FollowListService followListService;

    @Operation(summary = "3-1. 팔로워 목록 조회", description = "사용자의 팔로워 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CelebeApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "요청 사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/followers")
    public ResponseEntity<FollowListDto> getFollowers(
        @Parameter(description = "조회할 사용자의 식별자. 6자리의 영문 소문자와 숫자 조합",
                   schema = @Schema(pattern = "^[a-z0-9]{6}$",
                   example = "000001")) @PathVariable String publicId
    ) {
        log.debug("팔로워 목록 조회 요청: publicId={}", publicId);
        FollowListDto followers = followListService.getFollowers(publicId);
        log.debug("팔로워 목록 조회 완료: publicId={}, count={}", publicId, followers.getTotalCount());
        return ResponseEntity.ok(followers);
    }

    @Operation(summary = "3-2. 팔로잉 목록 조회", description = "사용자의 팔로잉 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CelebeApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "요청 사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/followings")
    public ResponseEntity<FollowListDto> getFollowings(
        @Parameter(description = "조회할 사용자의 식별자. 6자리의 영문 소문자와 숫자 조합",
                   schema = @Schema(pattern = "^[a-z0-9]{6}$",
                   example = "000001")) @PathVariable String publicId
    ) {
        log.debug("팔로잉 목록 조회 요청: publicId={}", publicId);
        FollowListDto following = followListService.getFollowings(publicId);
        log.debug("팔로잉 목록 조회 완료: publicId={}, count={}", publicId, following.getTotalCount());
        return ResponseEntity.ok(following);
    }
}