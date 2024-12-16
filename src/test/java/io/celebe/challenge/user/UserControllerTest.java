package io.celebe.challenge.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("유효한 publicId로 조회시 프로필을 반환한다")
    void returnsProfile_whenValidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/profiles/abc123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicId").value("abc123"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.nickname").value("testuser"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.thumbnailUrl").value("https://example.com/thumb/1.jpg"));
    }

    @Test
    @DisplayName("존재하지 않는 publicId로 조회시 404를 반환한다")
    void returns404_whenInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/profiles/nonexistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("비활성화된 사용자 조회시 404를 반환한다")
    void returns404_whenInactiveUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/profiles/def456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}