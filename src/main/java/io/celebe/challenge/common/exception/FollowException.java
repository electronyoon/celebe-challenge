package io.celebe.challenge.common.exception;

import org.springframework.http.HttpStatus;

public class FollowException extends ApiException {
    public FollowException(String message, HttpStatus status) {
        super(message, status);
    }
}
