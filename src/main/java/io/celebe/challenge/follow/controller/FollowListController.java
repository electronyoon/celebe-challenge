package io.celebe.challenge.follow.controller;

import io.celebe.challenge.follow.service.FollowListService;
import io.celebe.challenge.model.dto.FollowListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/{publicId}")
@RequiredArgsConstructor
public class FollowListController {
    private final FollowListService followListService;

    @GetMapping("/followers")
    public ResponseEntity<FollowListResponseDto> getFollowers(@PathVariable String publicId) {
        return ResponseEntity.ok(followListService.getFollowers(publicId));
    }

    @GetMapping("/following")
    public ResponseEntity<FollowListResponseDto> getFollowing(@PathVariable String publicId) {
        return ResponseEntity.ok(followListService.getFollowing(publicId));
    }
}