-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE IF NOT EXISTS tags_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE tags_seq

--changeset soroka andrei:2
CREATE SEQUENCE tweet_tags_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE tweet_tags_seq

--changeset soroka andrei:3
CREATE TABLE IF NOT EXISTS tags
(
    id              BIGINT PRIMARY KEY,
    tag_name        VARCHAR(255) NOT NULL,
    tweets_quantity BIGINT DEFAULT 1
);
--rollback DROP TABLE tags

--changeset soroka andrei:4
CREATE TABLE IF NOT EXISTS tweet_tags
(
    id       BIGINT PRIMARY KEY,
    tag_id   BIGINT NOT NULL,
    tweet_id BIGINT NOT NULL
);
--rollback DROP TABLE tweet_tags

--changeset soroka andrei:5
CREATE INDEX tags_tag_name_idx ON tags (tag_name);
--rollback DROP INDEX tags_tag_name_idx

--changeset soroka andrei:6
CREATE INDEX tweet_tags_tag_id_idx ON tweet_tags (tag_id);
--rollback DROP INDEX tweet_tags_tag_id_idx

--changeset soroka andrei:7
CREATE INDEX tweet_tags_tweet_id_idx ON tweet_tags (tweet_id);
--rollback DROP INDEX tweet_tags_tweet_id_idx