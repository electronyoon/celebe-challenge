INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000001', 'user1@example.com', 'user1', 'User One', 'https://example.com/thumb/user1.jpg', 2, 1, 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000002', 'user2@example.com', 'user2', 'User Two', 'https://example.com/thumb/user2.jpg', 1, 2, 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000003', 'user3@example.com', 'user3', 'User Three', 'https://example.com/thumb/user3.jpg', 1, 1, 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000004', 'user4@example.com', 'user4', 'User Four', 'https://example.com/thumb/user4.jpg', 0, 0, 0,
        DATETIME('now'), DATETIME('now'));

-- 000001: 1명 팔로우 중(000002), 2명의 팔로워 있음(000002, 000003), 000003 언팔로우 이력 있음
-- 000002: 2명 팔로우 중(000001, 000003), 1명의 팔로워 있음(000001)
-- 000003: 1명 팔로우 중(000001), 1명의 팔로워 있음(000002)
-- 000004: 비활성화된 계정

-- 000001 -> 000002 (활성 팔로우)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000001' AND u2.public_id = '000002';

-- 000002 -> 000001 (맞팔)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000002' AND u2.public_id = '000001';

-- 000002 -> 000003 (단방향 팔로우)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000002' AND u2.public_id = '000003';

-- 000003 -> 000001 (단방향 팔로우)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000003' AND u2.public_id = '000001';

-- 000001 -> 000003 (과거에 팔로우했다가 언팔로우한 관계)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 0, DATETIME('now', '-1 day'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000001' AND u2.public_id = '000003';