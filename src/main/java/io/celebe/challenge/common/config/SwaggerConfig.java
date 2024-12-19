package io.celebe.challenge.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Celebe Challenge",
        version = "1.0",
        description = """
                ## 개요
                Celebe 백엔드 개발자 채용 사전 과제 제출 프로젝트입니다. (제출자: 김상우)

                ## public ID
                셀러비 플랫폼과 마찬가지로, 영문 소문자와 숫자 조합의 `6자리의 고유 식별자`를 id로 사용합니다. 모든 API는 이 `고유 식별자`로 요청하도록 설계했습니다.
                
                ## celebe-local.db
                테스트 데이터가 주어진 db가 있습니다. local 프로파일 환경에서만 작동합니다.

                ## 테스트 데이터 설명
                다음 유저 중 원하는 유저를 선택해 API로 요청해 보시면 됩니다.

                - 유저 nblxyl: 아무 팔로잉 관계가 없는 활성화된 유저. 프로필 조회용으로 사용하세요.
                - 유저 000001: 맞팔 1명(<->000002), 팔로잉 1명(->000002), 팔로워 2명(<-000002, 000003), 언팔로우 1명(->000003)
                - 유저 000002: 팔로잉 2명(->000001, 000004), 팔로워 1명(<-000001)
                - 유저 000003: 팔로잉 1명(->000001)
                - 유저 000004: 팔로워 1명(<-000002)
                - 유저 000005: 비활성화된 계정, 과거 맞팔 1명(<->000001), 과거 팔로잉 1명(->000001), 과거 팔로워 1명(<-000001)
                """
    )
)
public class SwaggerConfig {
}
