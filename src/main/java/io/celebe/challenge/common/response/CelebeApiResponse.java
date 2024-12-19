package io.celebe.challenge.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "API 응답 공통 형식")
public class CelebeApiResponse<T> {

    @Schema(description = "응답 생성 시각",
            example = "2024-12-19T14:30:00")
    private final LocalDateTime timestamp;

    @Schema(description = "HTTP 상태 코드",
            example = "200")
    private final int status;

    @Schema(description = "응답 메시지",
            example = "Success")
    private final String message;

    @Schema(description = "응답 데이터")
    private final T data;

    public static <T> CelebeApiResponse<T> success(T data) {
        return CelebeApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }
}