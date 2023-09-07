-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE lists_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE lists_seq

--changeset soroka andrei:2
CREATE SEQUENCE lists_followers_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE lists_followers_seq

--changeset soroka andrei:3
CREATE SEQUENCE lists_members_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE lists_members_seq

--changeset soroka andrei:4
CREATE SEQUENCE lists_wallpaper_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE lists_wallpaper_seq

--changeset soroka andrei 5:
CREATE SEQUENCE pinned_lists_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE pinned_lists_seq

--changeset soroka andrei:6
CREATE TABLE IF NOT EXISTS lists
(
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255),
    private       BOOLEAN DEFAULT FALSE,
    alt_wallpaper VARCHAR(255),
    wallpaper     VARCHAR(255),
    list_owner_id BIGINT       NOT NULL
);
--rollback DROP TABLE lists

--changeset soroka andrei:7
CREATE TABLE IF NOT EXISTS lists_followers
(
    id          BIGINT PRIMARY KEY,
    list_id     BIGINT NOT NULL,
    follower_id BIGINT NOT NULL
);
--rollback DROP TABLE lists_followers

--changeset soroka andrei:8
CREATE TABLE IF NOT EXISTS lists_members
(
    id        BIGINT PRIMARY KEY,
    list_id   BIGINT NOT NULL,
    member_id BIGINT NOT NULL
);
--rollback DROP TABLE lists_members

--changeset soroka andrei:9
CREATE TABLE IF NOT EXISTS pinned_lists
(
    id             BIGINT PRIMARY KEY,
    pinned_date    TIMESTAMP DEFAULT current_timestamp,
    list_id        BIGINT,
    pinned_user_id BIGINT
);
--rollback DROP TABLE pinned_lists

--changeset soroka andrei:10
CREATE INDEX IF NOT EXISTS lists_list_owner_id_idx ON lists (list_owner_id);
--rollback DROP INDEX lists_list_owner_id_idx

--changeset soroka andrei:11
CREATE INDEX IF NOT EXISTS lists_followers_list_id_idx ON lists_followers (list_id);
--rollback DROP INDEX lists_followers_list_id_idx

--changeset soroka andrei:12
CREATE INDEX IF NOT EXISTS lists_followers_follower_id_idx ON lists_followers (follower_id);
--rollback DROP INDEX lists_followers_follower_id_idx

--changeset soroka andrei:13
CREATE INDEX IF NOT EXISTS lists_members_list_id_idx ON lists_members (list_id);
--rollback DROP INDEX lists_members_list_id_idx

--changeset soroka andrei:14
CREATE INDEX IF NOT EXISTS lists_members_member_id_idx ON lists_members (member_id);
--rollback DROP INDEX lists_members_member_id_idx

--changeset soroka andrei:15
CREATE INDEX IF NOT EXISTS pinned_lists_list_id_idx ON pinned_lists (list_id);
--rollback DROP INDEX pinned_lists_list_id_idx

--changeset soroka andrei:16
CREATE INDEX IF NOT EXISTS pinned_lists_pinned_user_id_idx ON pinned_lists (pinned_user_id);
--rollback DROP INDEX pinned_lists_pinned_user_id_idx
