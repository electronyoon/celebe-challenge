-- 프로필 조회 테스트용 기본 사용자
INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000001', 'active@example.com', 'activeuser', 'Active User', 'https://example.com/thumb/1.jpg', 0, 0, 1,
        DATETIME('now'), DATETIME('now'));

-- 비활성화된 사용자
INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000002', 'inactive@example.com', 'inactive', 'Inactive User', 'https://example.com/thumb/2.jpg', 0, 0, 0,
        DATETIME('now'), DATETIME('now'));

-- 팔로워가 있는 사용자
INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000003', 'followed@example.com', 'followed', 'Followed User', 'https://example.com/thumb/3.jpg', 2, 1, 1,
        DATETIME('now'), DATETIME('now'));