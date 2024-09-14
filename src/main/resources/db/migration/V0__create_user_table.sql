CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_telegram_id  BIGINT       NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS notes
(
    id                   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content_details_id   UUID UNIQUE NOT NULL,
    status_of_completion BOOL        NOT NULL
);

CREATE TYPE content_type AS ENUM (
    'PODCAST',
    'VIDEO',
    'BOOK',
    'ARTICLE'
    );

CREATE TABLE IF NOT EXISTS content_details
(
    id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content_short_name VARCHAR(255) UNIQUE,
    content_url        VARCHAR(255),
    content_type       content_type NOT NULL
);

CREATE TABLE IF NOT EXISTS comment
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    text_message TEXT NOT NULL,
    timecode     INTEGER, -- IN SECONDS
    page         INTEGER
);

CREATE TABLE IF NOT EXISTS note_comments
(
    id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content_details_id UUID REFERENCES content_details (id) NOT NULL UNIQUE,
    comment_id         UUID                                 NOT NULL -- ONE TO MANY. MANY COMMENTS TO ONE DESCRIPTION ID
);

CREATE TABLE IF NOT EXISTS user_note_links
(
    id      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID REFERENCES users (id) NOT NULL,
    note_id UUID REFERENCES notes (id) NOT NULL UNIQUE -- ONE TO MANY: MANY NOTES PER USER
);