package io.celebe.challenge.domain.follow.controller;

import io.celebe.challenge.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

}
