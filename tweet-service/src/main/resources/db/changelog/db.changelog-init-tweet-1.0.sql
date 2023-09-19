-- liquibase formatted sql

--changeset soroka andrei:1
CREATE SEQUENCE tweets_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE tweets_seq

--changeset soroka andrei:2
CREATE SEQUENCE tweet_image_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE tweet_image_seq

--changeset soroka andrei:3
CREATE SEQUENCE polls_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE polls_seq

--changeset soroka andrei:4
CREATE SEQUENCE poll_choices_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE poll_choices_seq

--changeset soroka andrei:5
create sequence poll_choice_voted_seq start 100 increment 1;
--rollback DROP SEQUENCE poll_choice_voted_seq

--changeset soroka andrei:6
CREATE SEQUENCE retweets_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE retweets_seq

--changeset soroka andrei:7
CREATE SEQUENCE liked_tweets_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE liked_tweets_seq

--changeset soroka andrei:8
CREATE SEQUENCE bookmarks_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE bookmarks_seq

--changeset soroka andrei:9
CREATE SEQUENCE gif_image_seq START 100 INCREMENT 1;
--rollback DROP SEQUENCE gif_image_seq

--changeset soroka andrei:10
CREATE TABLE IF NOT EXISTS polls
(
    id        BIGINT PRIMARY KEY,
    date_time TIMESTAMP
);
--rollback DROP TABLE polls

--changeset soroka andrei:11
CREATE TABLE IF NOT EXISTS gif_image
(
    id             BIGINT PRIMARY KEY,
    height BIGINT,
    url    VARCHAR(255),
    width  BIGINT
);
--rollback DROP TABLE gif_image

--changeset soroka andrei:12
CREATE TABLE IF NOT EXISTS tweets
(
    id                 BIGINT PRIMARY KEY,
    text               VARCHAR(255) NOT NULL,
    date_time          TIMESTAMP    DEFAULT current_timestamp,
    scheduled_date     TIMESTAMP,
    addressed_username VARCHAR(255),
    addressed_id       BIGINT,
    addressed_tweet_id BIGINT,
    reply_type         VARCHAR(255) DEFAULT 'EVERYONE',
    link               VARCHAR(255),
    link_title         VARCHAR(255),
    link_description   VARCHAR(255),
    link_cover         VARCHAR(255),
    image_description  VARCHAR(255),
    deleted            BOOLEAN      DEFAULT FALSE,
    link_cover_size    VARCHAR(255),
    author_id          BIGINT       NOT NULL,
    list_id            BIGINT,
    poll_id            BIGINT REFERENCES polls(id),
    gif_image_id       BIGINT REFERENCES gif_image(id)
);
--rollback DROP TABLE tweets

--changeset soroka andrei:13
CREATE TABLE IF NOT EXISTS tweet_images
(
    id  BIGINT PRIMARY KEY,
    src VARCHAR(255)
);
--rollback DROP TABLE tweet_images

--changeset soroka andrei:14
CREATE TABLE IF NOT EXISTS tweets_images
(
    tweet_id  BIGINT REFERENCES tweets(id),
    images_id BIGINT REFERENCES tweet_images(id)
);
--rollback DROP TABLE tweets_images

--changeset soroka andrei:15
CREATE TABLE IF NOT EXISTS tweet_quote
(
    tweet_id       BIGINT PRIMARY KEY REFERENCES tweets(id),
    quote_tweet_id BIGINT REFERENCES tweets(id)
);
--rollback DROP TABLE tweet_quote

--changeset soroka andrei:16
CREATE TABLE IF NOT EXISTS replies
(
    tweet_id BIGINT REFERENCES tweets(id),
    reply_id BIGINT REFERENCES tweets(id)
);
--rollback DROP TABLE replies

--changeset soroka andrei:17
CREATE TABLE IF NOT EXISTS quotes
(
    tweet_id BIGINT REFERENCES tweets(id),
    quote_id BIGINT REFERENCES tweets(id)
);
--rollback DROP TABLE quotes

--changeset soroka andrei:18
CREATE TABLE IF NOT EXISTS poll_choices
(
    id     BIGINT PRIMARY KEY,
    choice VARCHAR(255) NOT NULL
);
--rollback DROP TABLE poll_choices

--changeset soroka andrei:19
CREATE TABLE IF NOT EXISTS polls_poll_choices
(
    poll_id         BIGINT REFERENCES polls(id),
    poll_choices_id BIGINT REFERENCES poll_choices(id)
);
--rollback DROP TABLE polls_poll_choices


--changeset soroka andrei:20
CREATE TABLE IF NOT EXISTS poll_choice_voted
(
    id             BIGINT PRIMARY KEY,
    poll_choice_id BIGINT NOT NULL,
    voted_user_id  BIGINT NOT NULL
);
--rollback DROP TABLE poll_choice_voted

--changeset soroka andrei:21
CREATE TABLE IF NOT EXISTS liked_tweets
(
    id             BIGINT PRIMARY KEY,
    liked_tweet_date TIMESTAMP DEFAULT current_timestamp,
    tweet_id         BIGINT NOT NULL,
    user_id          BIGINT NOT NULL
);
--rollback DROP TABLE liked_tweets

--changeset soroka andrei:22
CREATE TABLE IF NOT EXISTS retweets
(
    id             BIGINT PRIMARY KEY,
    retweet_date TIMESTAMP DEFAULT current_timestamp,
    tweet_id         BIGINT NOT NULL,
    user_id          BIGINT NOT NULL
);
--rollback DROP TABLE retweets

--changeset soroka andrei:23
CREATE TABLE IF NOT EXISTS tagged_image_users
(
    tweet_id             BIGINT REFERENCES tweets(id),
    tagged_image_user_id BIGINT
);
--rollback DROP TABLE tagged_image_users

--changeset soroka andrei:24
CREATE TABLE IF NOT EXISTS bookmarks
(
    id            BIGINT PRIMARY KEY,
    bookmark_date TIMESTAMP DEFAULT current_timestamp,
    tweet_id      BIGINT NOT NULL,
    user_id       BIGINT NOT NULL
);
--rollback DROP TABLE bookmarks

--changeset soroka andrei:25
CREATE INDEX IF NOT EXISTS bookmarks_user_id_idx ON bookmarks (user_id);
--rollback DROP INDEX bookmarks_user_id_idx

--changeset soroka andrei:26
CREATE INDEX IF NOT EXISTS bookmarks_tweet_id_idx ON bookmarks (tweet_id);
--rollback DROP INDEX bookmarks_tweet_id_idx

--changeset soroka andrei:27
CREATE INDEX IF NOT EXISTS liked_tweets_user_id_idx ON liked_tweets (user_id);
--rollback DROP INDEX liked_tweets_user_id_idx

--changeset soroka andrei:28
CREATE INDEX IF NOT EXISTS liked_tweets_tweet_id_idx ON liked_tweets (tweet_id);
--rollback DROP INDEX liked_tweets_tweet_id_idx

--changeset soroka andrei:29
CREATE INDEX IF NOT EXISTS  poll_choice_voted_voted_user_id_idx ON poll_choice_voted (voted_user_id);
--rollback DROP INDEX poll_choice_voted_voted_user_id_idx

--changeset soroka andrei:30
CREATE INDEX IF NOT EXISTS  poll_choice_voted_poll_choice_id_idx ON poll_choice_voted (poll_choice_id);
--rollback DROP INDEX poll_choice_voted_poll_choice_id_idx

--changeset soroka andrei:31
CREATE INDEX IF NOT EXISTS  retweets_user_id_idx ON retweets (user_id);
--rollback DROP INDEX retweets_user_id_idx

--changeset soroka andrei:32
CREATE INDEX IF NOT EXISTS  retweets_tweet_id_idx ON retweets (tweet_id);
--rollback DROP INDEX retweets_tweet_id_idx

--changeset soroka andrei:33
CREATE INDEX IF NOT EXISTS  tweets_author_id_idx ON tweets (author_id);
--rollback DROP INDEX tweets_author_id_idx
