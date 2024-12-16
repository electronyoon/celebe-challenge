package io.celebe.challenge.user;

import io.celebe.challenge.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Sql("/sql/profile-api-test.sql")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("존재하는 프로필 조회시 200 OK와 프로필 정보를 반환한다")
    void getExistingProfile() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}", "000001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicId").value("000001"))
                .andExpect(jsonPath("$.email").value("active@example.com"))
                .andExpect(jsonPath("$.nickname").value("activeuser"))
                .andExpect(jsonPath("$.name").value("Active User"))
                .andExpect(jsonPath("$.thumbnailUrl").value("https://example.com/thumb/1.jpg"))
                .andExpect(jsonPath("$.followerCount").value(0))
                .andExpect(jsonPath("$.followingCount").value(0));
    }

    @Test
    @DisplayName("존재하지 않는 프로필 조회시 404 Not Found를 반환한다")
    void getNonExistentProfile() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}", "999999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("비활성화된 프로필 조회시 404 Not Found를 반환한다")
    void getInactiveProfile() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}", "000002"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("팔로워가 있는 프로필 조회시 팔로워 수가 정상적으로 반환된다")
    void getProfileWithFollowers() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}", "000003"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicId").value("000003"))
                .andExpect(jsonPath("$.followerCount").value(2))
                .andExpect(jsonPath("$.followingCount").value(1));
    }

}