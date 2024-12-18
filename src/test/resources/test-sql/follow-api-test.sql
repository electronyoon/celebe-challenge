INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000001', 'user1@example.com', 'user1', 'User One', 'https://example.com/thumb/user1.jpg', 2, 1, 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000002', 'user2@example.com', 'user2', 'User Two', 'https://example.com/thumb/user2.jpg', 1, 2, 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000003', 'user3@example.com', 'user3', 'User Three', 'https://example.com/thumb/user3.jpg', 0, 1, 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000004', 'user4@example.com', 'user4', 'User Four', 'https://example.com/thumb/user4.jpg', 1, 0, 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, follower_count, following_count, is_active, created_at, updated_at)
VALUES ('000005', 'user5@example.com', 'user5', 'User Five', 'https://example.com/thumb/user5.jpg', 1, 1, 0,
        DATETIME('now'), DATETIME('now'));

-- 000001: 맞팔 1명(<->000002), 팔로잉 1명(->000002), 팔로워 2명(<-000002, 000003), 언팔로우 1명(->000003)
-- 000002: 팔로잉 2명(->000001, 000004), 팔로워 1명(<-000001)
-- 000003: 팔로잉 1명(->000001)
-- 000004: 팔로워 1명(<-000002)
-- 000005: 비활성화된 계정, 과거 맞팔 1명(<->000001), 과거 팔로잉 1명(->000001), 과거 팔로워 1명(<-000001),

-- 000001 -> 000002
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000001' AND u2.public_id = '000002';

-- 000001 -> 000003 (예전에 팔로우, 지금은 언팔로우)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 0, DATETIME('now', '-1 day'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000001' AND u2.public_id = '000003';

-- 000001 -> 000005 (예전에 팔로우, 지금은 000005 비활성화)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now', '-1 day'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000001' AND u2.public_id = '000005';

-- 000002 -> 000001
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000002' AND u2.public_id = '000001';

-- 000002 -> 000004
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000002' AND u2.public_id = '000004';

-- 000003 -> 000001
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000003' AND u2.public_id = '000001';

-- 000005 -> 000001 (예전에 팔로우, 지금은 000005 비활성화)
INSERT INTO follows (follower_id, following_id, is_active, created_at, updated_at)
SELECT u1.id, u2.id, 1, DATETIME('now', '-1 day'), DATETIME('now')
FROM users u1, users u2
WHERE u1.public_id = '000005' AND u2.public_id = '000001';