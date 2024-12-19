package io.celebe.challenge.follow;

import io.celebe.challenge.CelebeChallengeApplicationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/test-sql/follow-api-test.sql")
class FollowApiTest extends CelebeChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("정상적인 팔로우 요청시 200 OK를 반환한다")
    void followSuccess() throws Exception {
        mockMvc.perform(post("/api/profiles/{publicId}/follow", "000003")
                        .header("CUSTOM-AUTH-ID", "000001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이미 팔로우한 계정을 팔로우할 경우 409 Conflict를 반환한다")
    void followDuplicate() throws Exception {
        mockMvc.perform(post("/api/profiles/{publicId}/follow", "000002")
                        .header("CUSTOM-AUTH-ID", "000001"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("자기 자신을 팔로우할 경우 400 Bad Request를 반환한다")
    void followSelf() throws Exception {
        mockMvc.perform(post("/api/profiles/{publicId}/follow", "000001")
                        .header("CUSTOM-AUTH-ID", "000001"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 계정을 팔로우할 경우 404 Not Found를 반환한다")
    void followNonExistent() throws Exception {
        mockMvc.perform(post("/api/profiles/{publicId}/follow", "000005")
                        .header("CUSTOM-AUTH-ID", "000001"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("정상적인 언팔로우 요청시 200 OK를 반환한다")
    void unfollowSuccess() throws Exception {
        mockMvc.perform(delete("/api/profiles/{publicId}/follow", "000002")
                        .header("CUSTOM-AUTH-ID", "000001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팔로우하지 않은 계정을 언팔로우할 경우 409 Conflict를 반환한다")
    void unfollowNonFollowing() throws Exception {
        mockMvc.perform(delete("/api/profiles/{publicId}/follow", "000003")
                        .header("CUSTOM-AUTH-ID", "000001"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

}