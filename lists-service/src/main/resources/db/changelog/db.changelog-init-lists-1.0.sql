-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE lists_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE lists_seq

--changeset soroka andrei:2
CREATE TABLE users
(
    id              BIGINT         NOT NULL,
    full_name       VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    about           VARCHAR(255),
    avatar          VARCHAR(255),
    private_profile BOOLEAN DEFAULT FALSE,
    active          BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);
--rollback DROP SEQUENCE users

--changeset soroka andrei:3
CREATE TABLE user_blocked
(
    user_id         BIGINT NOT NULL REFERENCES users (id),
    blocked_user_id BIGINT NOT NULL REFERENCES users (id)
);
--rollback DROP SEQUENCE user_blocked

--changeset soroka andrei:4
CREATE INDEX user_blocked_user_id_idx ON user_blocked (user_id);
CREATE INDEX user_blocked_blocked_user_id_idx ON user_blocked (blocked_user_id);

--changeset soroka andrei 5:
CREATE TABLE user_subscriptions
(
    user_id       INT8 NOT NULL REFERENCES users (id),
    subscriber_id INT8 NOT NULL REFERENCES users (id)
);
--rollback DROP SEQUENCE user_subscriptions

--changeset soroka andrei 6:
CREATE TABLE user_subscriptions
CREATE INDEX user_subscriptions_user_id_idx ON user_subscriptions (user_id);
CREATE INDEX user_subscriptions_subscriber_id_idx ON user_subscriptions (subscriber_id);


--changeset soroka andrei:7
CREATE TABLE IF NOT EXISTS lists
(
    id            BIGINT PRIMARY KEY,
    list_name     VARCHAR(255) NOT NULL,
    description   VARCHAR(255),
    private       BOOLEAN DEFAULT FALSE,
    alt_wallpaper VARCHAR(255),
    wallpaper     VARCHAR(255),
    list_owner_id BIGINT         NOT NULL REFERENCES users (id),
);
--rollback DROP TABLE lists

--changeset soroka andrei:8
CREATE INDEX ON lists (list_owner_id);

--changeset soroka andrei:9
CREATE TABLE IF NOT EXISTS lists_followers
(
    list_id     BIGINT NOT NULL REFERENCES lists (id),
    follower_id BIGINT NOT NULL REFERENCES users (id)
);
--rollback DROP TABLE lists_followers

--changeset soroka andrei:10
CREATE INDEX lists_followers_list_id_idx ON lists_followers (list_id);
CREATE INDEX lists_followers_follower_id_idx ON lists_followers (follower_id);

--changeset soroka andrei:11
CREATE TABLE IF NOT EXISTS lists_members
(
    list_id   BIGINT NOT NULL REFERENCES lists (id),
    member_id BIGINT NOT NULL REFERENCES users (id)
);
--rollback DROP TABLE lists_members

--changeset soroka andrei:12
CREATE INDEX lists_members_list_id_idx ON lists_members (list_id);
CREATE INDEX lists_members_member_id_idx ON lists_members (member_id);

--changeset soroka andrei:13
CREATE TABLE IF NOT EXISTS pinned_lists
(
    list_id        BIGINT NOT NULL REFERENCES lists (id),
    pinned_user_id BIGINT NOT NULL REFERENCES users (id),
    pinned_date    TIMESTAMP DEFAULT current_timestamp
);
--rollback DROP TABLE pinned_lists

--changeset soroka andrei:14
CREATE INDEX pinned_lists_list_id_idx ON pinned_lists (list_id);
CREATE INDEX pinned_lists_pinned_user_id_idx ON pinned_lists (pinned_user_id);



--changeset soroka andrei:15
CREATE TABLE lists_followers_demo
(
    id          BIGINT NOT NULL,
    list_id     BIGINT NOT NULL,
    follower_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE lists_members_demo
(
    id        BIGINT NOT NULL,
    list_id   BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE pinned_lists_demo
(
    id             BIGSERIAL NOT NULL,
    pinned_date    TIMESTAMP DEFAULT current_timestamp,
    list_id        BIGINT,
    pinned_user_id BIGINT,
    PRIMARY KEY (id)
);
