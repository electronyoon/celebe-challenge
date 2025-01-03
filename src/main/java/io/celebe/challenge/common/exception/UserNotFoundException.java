package io.celebe.challenge.common.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CelebeApiException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
