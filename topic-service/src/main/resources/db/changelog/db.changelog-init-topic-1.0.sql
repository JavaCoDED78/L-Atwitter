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

create index topic_followers_user_id_idx on topic_followers (user_id);
create index topic_followers_topic_id_idx on topic_followers (topic_id);
--rollback DROP TABLE topic_followers

--changeset soroka andrei:6
CREATE TABLE IF NOT EXISTS topic_not_interested
(
    id       BIGINT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL
);

create index topic_not_interested_user_id_idx on topic_not_interested (user_id);
create index topic_not_interested_topic_id_idx on topic_not_interested (topic_id);
--rollback DROP TABLE topic_not_interested

--changeset soroka andrei:7
CREATE TABLE IF NOT EXISTS users
(
    id              BIGINT PRIMARY KEY,
    full_name       VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    private_profile BOOLEAN DEFAULT false,
);
--rollback DROP TABLE users

--changeset soroka andrei:8
CREATE TABLE IF NOT EXISTS user_blocked
(
    user_id         BIGINT NOT NULL,
    blocked_user_id BIGINT NOT NULL
);
--rollback DROP TABLE user_blocked

--changeset soroka andrei:9
CREATE TABLE IF NOT EXISTS user_subscriptions
(
    subscriber_id BIGINT NOT NULL,
    user_id       BIGINT NOT NULL
);
--rollback DROP TABLE user_subscriptions

--changeset soroka andrei:10
ALTER TABLE topic_followers
    add constraint topic_followers_topic_id foreign key (topic_id) references topics;
ALTER TABLE topic_followers
    add constraint topic_followers_user_id foreign key (user_id) references users;
ALTER TABLE topic_not_interested
    add constraint topic_not_interested_topic_id foreign key (topic_id) references topics;
ALTER TABLE topic_not_interested
    add constraint topic_not_interested_user_id foreign key (user_id) references users;
ALTER TABLE user_blocked
    add constraint user_blocked_blocked_user_id foreign key (blocked_user_id) references users;
ALTER TABLE user_blocked
    add constraint user_blocked_user_id foreign key (user_id) references users;
ALTER TABLE user_subscriptions
    add constraint user_subscriptions_user_id foreign key (user_id) references users;
ALTER TABLE user_subscriptions
    add constraint user_subscriptions_subscriber_id foreign key (subscriber_id) references users;

--changeset soroka andrei:10
CREATE TABLE users
(
        id              BIGINT         not null,
        full_name       VARCHAR(255) not null,
        username        VARCHAR(255) not null,
        private_profile BOOLEAN DEFAULT false,
        primary key (id)
);

CREATE TABLE user_blocked
(
    user_id         BIGINT NOT NULL REFERENCES users (id),
    blocked_user_id BIGINT NOT NULL REFERENCES users (id)
);

CREATE INDEX user_blocked_user_id_idx on user_blocked (user_id);
CREATE INDEX user_blocked_blocked_user_id_idx on user_blocked (blocked_user_id);

CREATE TABLE user_subscriptions
(
    user_id       BIGINT not null references users (id),
    subscriber_id BIGINT not null references users (id)
);

CREATE INDEX user_subscriptions_user_id_idx on user_subscriptions (user_id);
CREATE INDEX user_subscriptions_subscriber_id_idx on user_subscriptions (subscriber_id);

-- liquibase formatted sql
