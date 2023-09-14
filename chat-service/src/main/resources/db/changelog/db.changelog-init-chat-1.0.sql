-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE chat_messages_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE chat_messages_seq

--changeset soroka andrei:2
CREATE SEQUENCE chats_participants_seq start 100 INCREMENT 1;
--rollback DROP SEQUENCE chats_participants_seq

--changeset soroka andrei:3
CREATE SEQUENCE chats_seq start 100 INCREMENT 1;
--rollback DROP SEQUENCE chats_seq

--changeset soroka andrei:4
CREATE TABLE IF NOT EXISTS chats
(
    id            BIGINT PRIMARY KEY,
    creation_date tIMESTAMP DEFAULT current_timestamp
);
--rollback DROP TABLE chats

--changeset soroka andrei:5
CREATE TABLE IF NOT EXISTS chat_messages
(
    id        BIGINT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    date      TIMESTAMP DEFAULT current_timestamp,
    text      VARCHAR(255),
    tweet_id  BIGINT,
    chat_id   BIGINT REFERENCES  chats(id),
    is_unread BOOLEAN   DEFAULT TRUE
);
--rollback DROP TABLE chat_messages

--changeset soroka andrei:6
CREATE TABLE IF NOT EXISTS chats_participants
(
    id        BIGINT PRIMARY KEY,
    left_chat BOOLEAN DEFAULT FALSE,
    user_id   BIGINT NOT NULL,
    chat_id   BIGINT REFERENCES  chats(id)
);
--rollback DROP TABLE chats_participants

--changeset soroka andrei:7
CREATE INDEX IF NOT EXISTS chats_participants_user_id_idx ON chats_participants (user_id);
--rollback DROP INDEX chats_participants_user_id_idx

--changeset soroka andrei:8
CREATE INDEX IF NOT EXISTS chats_participants_chat_id_idx ON chats_participants (chat_id);
--rollback DROP INDEX chats_participants_chat_id_idx

--changeset soroka andrei:9
CREATE INDEX IF NOT EXISTS chat_messages_author_id_idx ON chat_messages (author_id);
--rollback DROP INDEX chat_messages_author_id_idx

--changeset soroka andrei:10
CREATE INDEX IF NOT EXISTS chat_messages_chat_id_idx ON chat_messages (chat_id);
--rollback DROP INDEX chat_messages_chat_id_idx
