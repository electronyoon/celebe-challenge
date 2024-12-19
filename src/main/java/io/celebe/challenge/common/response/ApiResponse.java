package io.celebe.challenge.common.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse<T> {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }
}