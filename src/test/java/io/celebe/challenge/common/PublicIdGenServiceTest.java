package io.celebe.challenge.common;

import io.celebe.challenge.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PublicIdGenServiceTest {
    @Mock
    private PublicIdGenerator generator;

    @Mock
    private UserMapper userMapper;

    private PublicIdGenService service;

    @BeforeEach
    void setUp() {
        service = new PublicIdGenService(generator, userMapper);
    }

    @Test
    @DisplayName("첫 번째 생성된 ID가 사용 가능하면 해당 ID를 반환한다")
    void shouldReturnFirstIdWhenAvailable() {
        String expectedId = "abc123";
        given(generator.generate()).willReturn(expectedId);
        given(userMapper.existsByPublicId(expectedId)).willReturn(false);

        String actualId = service.generateUniquePublicId();

        assertThat(actualId).isEqualTo(expectedId);
        verify(generator, times(1)).generate();
        verify(userMapper, times(1)).existsByPublicId(expectedId);
    }

    @Test
    @DisplayName("ID 충돌 시 새로운 ID를 생성한다")
    void shouldGenerateNewIdWhenCollision() {
        String firstId = "abc123";
        String secondId = "def456";
        given(generator.generate())
                .willReturn(firstId)
                .willReturn(secondId);
        given(userMapper.existsByPublicId(firstId)).willReturn(true);
        given(userMapper.existsByPublicId(secondId)).willReturn(false);

        String actualId = service.generateUniquePublicId();

        assertThat(actualId).isEqualTo(secondId);
        verify(generator, times(2)).generate();
        verify(userMapper, times(1)).existsByPublicId(firstId);
        verify(userMapper, times(1)).existsByPublicId(secondId);
    }
}
