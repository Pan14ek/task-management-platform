CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id          UUID PRIMARY KEY, -- Receive from the Auth Service
    full_name   VARCHAR(255),
    email       VARCHAR(255) UNIQUE,
    phone       VARCHAR(20) UNIQUE,
    avatar_url  TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
