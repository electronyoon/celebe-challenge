package io.celebe.challenge.user;

import io.celebe.challenge.model.User;
import io.celebe.challenge.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("프로필 서비스 테스트")
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유효한 publicId인 경우 프로필을 반환한다")
    void returnsProfile_whenValidId() {
        User profile = userService.getProfile("abc123");

        assertNotNull(profile);
        assertEquals("abc123", profile.getPublicId());
        assertEquals("test@example.com", profile.getEmail());
        assertEquals("testuser", profile.getNickname());
        assertEquals("Test User", profile.getName());
    }

    @Test
    @DisplayName("존재하지 않는 publicId인 경우 예외가 발생한다")
    void throwsException_whenInvalidId() {
        assertThrows(ResponseStatusException.class, () ->
                userService.getProfile("nonexistent")
        );
    }

    @Test
    @DisplayName("비활성화된 사용자인 경우 예외가 발생한다")
    void throwsException_whenInactiveUser() {
        assertThrows(ResponseStatusException.class, () ->
                userService.getProfile("def456")
        );
    }
}
