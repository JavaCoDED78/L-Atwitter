-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE users_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE users_seq

--changeset soroka andrei:2
CREATE TABLE IF NOT EXISTS users
(
    id                    BIGINT PRIMARY KEY,
    about                 VARCHAR(255),
    activation_code       VARCHAR(255),
    active                BOOLEAN      DEFAULT FALSE,
    avatar                VARCHAR(255),
    background_color      VARCHAR(255) DEFAULT 'DEFAULT',
    color_scheme          VARCHAR(255) DEFAULT 'BLUE',
    birthday              VARCHAR(255),
    country               VARCHAR(255),
    country_code          VARCHAR(255),
    email                 VARCHAR(255) unique not null,
    full_name             VARCHAR(255)        not null,
    gender                VARCHAR(255),
    language              VARCHAR(255),
    like_count            BIGINT       DEFAULT 0,
    location              VARCHAR(255),
    media_tweet_count     BIGINT       DEFAULT 0,
    muted_direct_messages BOOLEAN      DEFAULT FALSE,
    notifications_count   BIGINT       DEFAULT 0,
    mentions_count        BIGINT       DEFAULT 0,
    password              VARCHAR(255),
    password_reset_code   VARCHAR(255),
    phone                 BIGINT,
    pinned_tweet_id       BIGINT,
    private_profile       BOOLEAN      DEFAULT FALSE,
    profile_customized    BOOLEAN      DEFAULT FALSE,
    profile_started       BOOLEAN      DEFAULT FALSE,
    registration_date     TIMESTAMP    DEFAULT current_timestamp,
    role                  VARCHAR(255) DEFAULT 'USER',
    tweet_count           BIGINT       DEFAULT 0,
    unread_messages_count BIGINT       DEFAULT 0,
    username              VARCHAR(255)        NOT NULL,
    wallpaper             VARCHAR(255),
    website               VARCHAR(255)
);
--rollback DROP TABLE users

--changeset soroka andrei:3
create table subscribers
(
    user_id       BIGINT REFERENCES users (id),
    subscriber_id BIGINT REFERENCES users (id)
);
--rollback DROP TABLE subscribers

--changeset soroka andrei:4
create table user_blocked
(
    user_id         BIGINT REFERENCES users (id),
    blocked_user_id BIGINT REFERENCES users (id)
);
--rollback DROP TABLE user_blocked

--changeset soroka andrei:5
create table user_follower_requests
(
    user_id     BIGINT REFERENCES users (id),
    follower_id BIGINT REFERENCES users (id)
);
--rollback DROP TABLE user_follower_requests

--changeset soroka andrei:6
create table user_muted
(
    user_id       BIGINT REFERENCES users (id),
    muted_user_id BIGINT REFERENCES users (id)
);
--rollback DROP TABLE user_muted

--changeset soroka andrei:7
create table user_subscriptions
(
    user_id       BIGINT REFERENCES users (id),
    subscriber_id BIGINT REFERENCES users (id)
);
--rollback DROP TABLE user_subscriptions
