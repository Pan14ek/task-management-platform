CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS tasks (
                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     title VARCHAR(255) NOT NULL,
                                     description TEXT,
                                     status VARCHAR(50) DEFAULT 'TO_DO',
                                     priority INT DEFAULT 1,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     due_date TIMESTAMP,
                                     assigned_user UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS comments (
                                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                        task_id UUID REFERENCES tasks(id) ON DELETE CASCADE,
                                        user_id UUID NOT NULL,
                                        content TEXT NOT NULL,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

