package io.celebe.challenge.follow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Sql("/test-sql/follow-api-test.sql")
class FollowListApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("팔로워 목록 조회 시 200 OK와 정상적인 목록을 반환한다")
    void getFollowersSuccess() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}/followers", "000001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(2))
                .andExpect(jsonPath("$.users[0].publicId").value("000002"))  // 맞팔 유저가 첫번째
                .andExpect(jsonPath("$.users[0].isFollowedBack").value(true))
                .andExpect(jsonPath("$.users[1].publicId").value("000003"))  // 단방향 팔로워가 두번째
                .andExpect(jsonPath("$.users[1].isFollowedBack").value(false));
    }

    @Test
    @DisplayName("팔로잉 목록 조회 시 200 OK와 정상적인 목록을 반환한다")
    void getFollowingSuccess() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}/following", "000001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(1))
                .andExpect(jsonPath("$.users[0].publicId").value("000002"))
                .andExpect(jsonPath("$.users[0].isFollowedBack").value(true));
    }

    @Test
    @DisplayName("존재하지 않는 사용자의 팔로워 목록 조회 시 404 Not Found를 반환한다")
    void getFollowersNonExistentUser() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}/followers", "999999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("존재하지 않는 사용자의 팔로잉 목록 조회 시 404 Not Found를 반환한다")
    void getFollowingNonExistentUser() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}/following", "999999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("비활성화된 사용자의 팔로워 목록 조회 시 404 Not Found를 반환한다")
    void getFollowersInactiveUser() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}/followers", "000005"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("팔로워가 없는 사용자의 팔로워 목록 조회 시 빈 목록을 반환한다")
    void getFollowersEmptyList() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}/followers", "000003"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(0))
                .andExpect(jsonPath("$.users").isEmpty());
    }
}