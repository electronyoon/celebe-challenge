package io.celebe.challenge.user;

import io.celebe.challenge.model.User;
import io.celebe.challenge.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql("/sql/profile-api-test.sql")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("정상적인 프로필 조회시 성공")
    void getProfileSuccess() {
        User user = userService.getProfile("000001");

        assertThat(user).isNotNull();
        assertThat(user.getPublicId()).isEqualTo("000001");
        assertThat(user.getEmail()).isEqualTo("active@example.com");
        assertThat(user.getNickname()).isEqualTo("activeuser");
        assertThat(user.getName()).isEqualTo("Active User");
        assertThat(user.getThumbnailUrl()).isEqualTo("https://example.com/thumb/1.jpg");
        assertThat(user.getFollowerCount()).isZero();
        assertThat(user.getFollowingCount()).isZero();
    }

    @Test
    @DisplayName("비활성화된 프로필 조회시 예외 발생")
    void getInactiveProfile() {
        assertThatThrownBy(() -> userService.getProfile("000002"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("존재하지 않는 프로필 조회시 예외 발생")
    void getNonExistentProfile() {
        assertThatThrownBy(() -> userService.getProfile("999999"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("statusCode")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("팔로워가 있는 프로필 조회시 카운트 확인")
    void getProfileWithFollowers() {
        User user = userService.getProfile("000003");

        assertThat(user).isNotNull();
        assertThat(user.getPublicId()).isEqualTo("000003");
        assertThat(user.getFollowerCount()).isEqualTo(2);
        assertThat(user.getFollowingCount()).isEqualTo(1);
    }

}