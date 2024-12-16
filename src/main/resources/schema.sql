-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    public_id CHAR(6) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    nickname VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    bio TEXT,
    link1 VARCHAR(2048),
    link2 VARCHAR(2048),
    thumbnail_url VARCHAR(2048),
    follower_count INTEGER NOT NULL DEFAULT 0,
    following_count INTEGER NOT NULL DEFAULT 0,
    post_count INTEGER NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT 1,
    deactivated_at DATETIME,
    created_at DATETIME NOT NULL DEFAULT (DATETIME('now')),
    updated_at DATETIME NOT NULL DEFAULT (DATETIME('now'))
);

-- Create index for frequently used columns
CREATE INDEX IF NOT EXISTS idx_users_public_id ON users(public_id);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_nickname ON users(nickname);

-- Follows table
CREATE TABLE IF NOT EXISTS follows (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    follower_id INTEGER NOT NULL,
    following_id INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT (DATETIME('now')),
    updated_at DATETIME NOT NULL DEFAULT (DATETIME('now')),
    FOREIGN KEY (follower_id) REFERENCES users(id),
    FOREIGN KEY (following_id) REFERENCES users(id)
);

-- Create index for relationship queries
CREATE INDEX IF NOT EXISTS idx_follows_follower ON follows(follower_id, is_active);
CREATE INDEX IF NOT EXISTS idx_follows_following ON follows(following_id, is_active);

-- Create unique index to prevent duplicate follows
CREATE UNIQUE INDEX IF NOT EXISTS idx_follows_unique
ON follows(follower_id, following_id)
WHERE is_active = 1;