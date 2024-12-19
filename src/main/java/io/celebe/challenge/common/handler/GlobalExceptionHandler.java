package io.celebe.challenge.common.handler;

import io.celebe.challenge.common.exception.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @NonNull Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", statusCode.value());
        response.put("error", HttpStatus.valueOf(statusCode.value()).getReasonPhrase());
        response.put("message", ex.getMessage());

        if (ex instanceof ApiException) {
            response.put("data", ((ApiException) ex).getErrorData());
        }

        return super.handleExceptionInternal(ex, response, headers, statusCode, request);
    }
}

