package io.celebe.challenge.common.handler;

import io.celebe.challenge.common.exception.CelebeApiException;
import io.celebe.challenge.common.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @NonNull Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request) {

        ErrorResponse response = ErrorResponse.of(
            statusCode.value(),
            HttpStatus.valueOf(statusCode.value()).getReasonPhrase(),
            ex.getMessage()
        );

        return super.handleExceptionInternal(ex, response, headers, statusCode, request);
    }


    @ExceptionHandler(CelebeApiException.class)
    protected ResponseEntity<ErrorResponse> handleApiException(CelebeApiException ex) {
        ErrorResponse response = ErrorResponse.of(
            ex.getStatus().value(),
            ex.getStatus().getReasonPhrase(),
            ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

}

