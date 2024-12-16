INSERT INTO users (public_id, email, nickname, name, thumbnail_url, is_active, created_at, updated_at)
VALUES ('abc123', 'test@example.com', 'testuser', 'Test User', 'https://example.com/thumb/1.jpg', 1,
        DATETIME('now'), DATETIME('now'));

INSERT INTO users (public_id, email, nickname, name, thumbnail_url, is_active, created_at, updated_at)
VALUES ('def456', 'inactive@example.com', 'inactive', 'Inactive User', 'https://example.com/thumb/2.jpg', 0,
        DATETIME('now'), DATETIME('now'));