package io.celebe.challenge.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CelebeApiException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
    private final Object errorData;
    private final String userId;

    public CelebeApiException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.errorData = null;
        this.userId = null;
    }

    public CelebeApiException(String message, HttpStatus status, Object errorData) {
        this.message = message;
        this.status = status;
        this.errorData = errorData;
        this.userId = null;
    }

    public CelebeApiException(String message, HttpStatus status, String userId) {
        this.message = message;
        this.status = status;
        this.errorData = null;
        this.userId = userId;
    }

    public CelebeApiException(String message, HttpStatus status, Object errorData, String userId) {
        this.message = message;
        this.status = status;
        this.errorData = errorData;
        this.userId = userId;
    }
}
