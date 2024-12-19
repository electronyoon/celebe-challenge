package io.celebe.challenge.user.controller;

import io.celebe.challenge.user.dto.UserProfileDto;
import io.celebe.challenge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{publicId}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable String publicId) {
        return ResponseEntity.ok(userService.getProfile(publicId));
    }
}
