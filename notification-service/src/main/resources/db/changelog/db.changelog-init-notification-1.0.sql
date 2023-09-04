-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE IF NOT EXISTS notifications_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE notifications_seq

--changeset soroka andrei:2
CREATE TABLE IF NOT EXISTS notifications
(
    id                BIGINT PRIMARY KEY,
    date              TIMESTAMP DEFAULT current_timestamp,
    notification_type VARCHAR(255) NOT NULL,
    list_id           BIGINT,
    notified_user_id  BIGINT NOT NULL,
    tweet_id          BIGINT,
    user_id           BIGINT NOT NULL,
    user_to_follow_id BIGINT
);
--rollback DROP TABLE notifications
