package io.celebe.challenge.common.response;

import io.celebe.challenge.common.exception.ApiException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                MethodParameter returnType,
                                MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request,
                                ServerHttpResponse response) {
        // ApiResponse나 ErrorResponse로 이미 래핑된 응답은 그대로 반환
        if (body instanceof ApiResponse || body instanceof ErrorResponse) {
            return body;
        }

        // 에러 응답 처리
        if (body instanceof ApiException) {
            ApiException ex = (ApiException) body;
            return ErrorResponse.of(
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage()
            );
        }

        // 정상 응답 래핑
        return ApiResponse.success(body);
    }
}