package io.celebe.challenge.user.controller;

import io.celebe.challenge.user.dto.UserProfileDto;
import io.celebe.challenge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{publicId}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable String publicId) {
        log.debug("프로필 조회 요청: publicId={}", publicId);
        UserProfileDto profile = userService.getProfile(publicId);
        log.debug("프로필 조회 완료: {}", profile);
        return ResponseEntity.ok(profile);
    }
}
