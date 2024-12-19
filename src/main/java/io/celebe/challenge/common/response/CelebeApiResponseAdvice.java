package io.celebe.challenge.common.response;

import io.celebe.challenge.common.exception.CelebeApiException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class CelebeApiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // springdoc-openapi 관련 패키지 제외
        String packageName = returnType.getContainingClass().getPackage().getName();
        return !packageName.startsWith("org.springdoc") &&
               !packageName.startsWith("springfox.documentation");
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                MethodParameter returnType,
                                MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request,
                                ServerHttpResponse response) {
        // ApiResponse나 ErrorResponse로 이미 래핑된 응답은 그대로 반환
        if (body instanceof CelebeApiResponse || body instanceof ErrorResponse) {
            return body;
        }

        // 에러 응답 처리
        if (body instanceof CelebeApiException) {
            CelebeApiException ex = (CelebeApiException) body;
            return ErrorResponse.of(
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage()
            );
        }

        // 정상 응답 래핑
        return CelebeApiResponse.success(body);
    }
}