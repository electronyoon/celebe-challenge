package io.celebe.challenge.user;

import io.celebe.challenge.CelebeChallengeApplicationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/api-test.sql")
class ProfileApiTest extends CelebeChallengeApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("존재하는 프로필 조회시 200 OK와 프로필 정보를 반환한다")
    void getExistingProfile() throws Exception {
        mockMvc.perform(get("/api/profiles/{publicId}", "000001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.publicId").value("000001"));
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
        mockMvc.perform(get("/api/profiles/{publicId}", "000005"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}