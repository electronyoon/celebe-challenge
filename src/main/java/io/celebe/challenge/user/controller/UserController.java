package io.celebe.challenge.user.controller;

import io.celebe.challenge.user.dto.UserProfileDto;
import io.celebe.challenge.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "프로필 API", description = "사용자 프로필 조회 API")
@RequestMapping("/api/profiles")
public class UserController {
    private final UserService userService;

    @Operation(summary = "프로필 조회", description = "사용자의 프로필 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/{publicId}")
    public ResponseEntity<UserProfileDto> getProfile(
        @Parameter(description = "조회할 사용자의 고유 식별자") @PathVariable String publicId
    ) {
        log.debug("프로필 조회 요청: publicId={}", publicId);
        UserProfileDto profile = userService.getProfile(publicId);
        log.debug("프로필 조회 완료: {}", profile);
        return ResponseEntity.ok(profile);
    }
}
