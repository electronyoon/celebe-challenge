package io.celebe.challenge.follow.controller;

import io.celebe.challenge.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/{publicId}")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    /**
     * 특정 프로필을 팔로우합니다.
     *
     * @param followingPublicId 팔로우할 사용자의 public ID
     * @param followerPublicId 현재 로그인한 사용자의 public ID (CUSTOM-AUTH-ID 헤더 이용)
     * @return 성공 시 200 OK
     */
    @PostMapping("/follow")
    public ResponseEntity<Void> follow( @PathVariable("publicId") String followingPublicId,
                                        @RequestHeader("CUSTOM-AUTH-ID") String followerPublicId ) {
        followService.follow(followerPublicId, followingPublicId);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 프로필을 언팔로우합니다.
     *
     * @param followingPublicId 언팔로우할 사용자의 public ID
     * @param followerPublicId 현재 로그인한 사용자의 public ID (CUSTOM-AUTH-ID 헤더 이용)
     * @return 성공 시 200 OK
     */
    @DeleteMapping("/follow")
    public ResponseEntity<Void> unfollow( @PathVariable("publicId") String followingPublicId,
                                          @RequestHeader("CUSTOM-AUTH-ID") String followerPublicId ) {
        followService.unfollow(followerPublicId, followingPublicId);
        return ResponseEntity.ok().build();
    }

}
