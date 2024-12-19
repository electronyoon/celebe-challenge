package io.celebe.challenge.follow.controller;

import io.celebe.challenge.follow.service.FollowListService;
import io.celebe.challenge.follow.dto.FollowListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/profiles/{publicId}")
@RequiredArgsConstructor
public class FollowListController {
    private final FollowListService followListService;

    @GetMapping("/followers")
    public ResponseEntity<FollowListDto> getFollowers(@PathVariable String publicId) {
        log.debug("팔로워 목록 조회 요청: publicId={}", publicId);
        FollowListDto followers = followListService.getFollowers(publicId);
        log.debug("팔로워 목록 조회 완료: publicId={}, count={}", publicId, followers.getTotalCount());
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following")
    public ResponseEntity<FollowListDto> getFollowing(@PathVariable String publicId) {
        log.debug("팔로잉 목록 조회 요청: publicId={}", publicId);
        FollowListDto following = followListService.getFollowing(publicId);
        log.debug("팔로잉 목록 조회 완료: publicId={}, count={}", publicId, following.getTotalCount());
        return ResponseEntity.ok(following);
    }
}