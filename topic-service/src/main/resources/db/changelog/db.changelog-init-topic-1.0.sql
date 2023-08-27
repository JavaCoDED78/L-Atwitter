-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE topics_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE topics_seq

--changeset soroka andrei:2
CREATE SEQUENCE topic_followers_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE topic_followers_seq

--changeset soroka andrei:3
CREATE SEQUENCE topic_not_interested_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE topic_not_interested_seq

--changeset soroka andrei:4
CREATE TABLE IF NOT EXISTS topics
(
    id             BIGINT PRIMARY KEY,
    topic_name     VARCHAR(255) NOT NULL UNIQUE,
    topic_category VARCHAR(255)
);
--rollback DROP TABLE topics

--changeset soroka andrei:5
CREATE TABLE IF NOT EXISTS topic_followers
(
    id       BIGINT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL
);
--rollback DROP TABLE topic_followers

--changeset soroka andrei:6
CREATE TABLE IF NOT EXISTS topic_not_interested
(
    id       BIGINT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL
);
--rollback DROP TABLE topic_not_interested
-- liquibase formatted sql
