# Celebe-Challenge
Celebe 백엔드 개발자 채용 사전 과제 제출 프로젝트입니다. (제출자: 김상우)

# ERD
```mermaid
erDiagram
    USERS {
        INTEGER id PK "autoincrement"
        CHAR(6) public_id UK "external ID e.g. a12bcd"
        VARCHAR email UK
        VARCHAR nickname UK
        TEXT bio "프로필 소개글"
        VARCHAR link1 "첫번째 링크"
        VARCHAR link2 "두번째 링크"
        VARCHAR thumbnail_url "url/{key}/{size}"
        INTEGER follower_count "denormalized counter"
        INTEGER following_count "denormalized counter"
        INTEGER post_count "denormalized counter"
        BOOLEAN is_active "soft deletion flag"
        DATETIME deactivated_at
        DATETIME created_at
        DATETIME updated_at
    }
    
    FOLLOWS {
        INTEGER id PK "autoincrement"
        INTEGER follower_id FK
        INTEGER following_id FK
        BOOLEAN is_activ "soft deletion flag"
        DATETIME created_at
        DATETIME updated_at
    }

    USERS ||--o{ FOLLOWS : "follower"
    USERS ||--o{ FOLLOWS : "following"
```

# API 명세

## 프로필 조회
- **URL**: `/api/profiles/{id}`
- **Method**: `GET`

## 프로필 팔로우
- **URL**: `/api/profiles/{id}/follow`
- **Method**: `POST`

## 프로필 언팔로우
- **URL**: `/api/profiles/{id}/follow`
- **Method**: `DELETE`

## 팔로워 목록 조회
- **URL**: `/api/profiles/{id}/followers`
- **Method**: `GET`

## 팔로잉 목록 조회
- **URL**: `/api/profiles/{id}/following`
- **Method**: `GET`
