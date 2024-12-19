package io.celebe.challenge.common;

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

    private PublicIdGenService service;

    @BeforeEach
    void setUp() {
        service = new PublicIdGenService(generator);
    }

    @Test
    @DisplayName("첫 번째 생성된 ID가 사용 가능하면 해당 ID를 반환한다")
    void shouldReturnFirstIdWhenAvailable() {
        String expectedId = "abc123";
        given(generator.generate()).willReturn(expectedId);

        String actualId = service.generateUniquePublicId();

        assertThat(actualId).isEqualTo(expectedId);
        verify(generator, times(1)).generate();
    }

}
