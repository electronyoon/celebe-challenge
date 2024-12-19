package io.celebe.challenge.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "에러 응답 공통 형식")
public class ErrorResponse {

    @Schema(description = "에러 발생 시각",
            example = "2024-12-19T14:30:00")
    private final LocalDateTime timestamp;

    @Schema(description = "HTTP 상태 코드",
            example = "404")
    private final int status;

    @Schema(description = "에러 종류",
            example = "Not Found")
    private final String error;

    @Schema(description = "에러 메시지",
            example = "존재하지 않는 유저입니다.")
    private final String message;
    public static ErrorResponse of(int status, String error, String message) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(error)
                .message(message)
                .build();
    }
}