-- liquibase formatted sql

--changeset soroka andrei:1
INSERT INTO topics (id, topic_category, topic_name) VALUES (1, null, 'My Blog');
INSERT INTO topics (id, topic_category, topic_name) VALUES (2, null, 'Technology');
INSERT INTO topics (id, topic_category, topic_name) VALUES (3, null, 'Java development');
INSERT INTO topics (id, topic_category, topic_name) VALUES (4, null, 'Entertainment');
INSERT INTO topics (id, topic_category, topic_name) VALUES (5, null, 'OnlyFans');
INSERT INTO topics (id, topic_category, topic_name) VALUES (6, null, 'Денис Матвеенко');
INSERT INTO topics (id, topic_category, topic_name) VALUES (7, 'GAMING', 'Witcher');
INSERT INTO topics (id, topic_category, topic_name) VALUES (8, 'GAMING', 'Minecraft');
INSERT INTO topics (id, topic_category, topic_name) VALUES (9, 'GAMING', 'Dead Space');
INSERT INTO topics (id, topic_category, topic_name) VALUES (10, 'GAMING', 'S.T.A.L.K.E.R.');
INSERT INTO topics (id, topic_category, topic_name) VALUES (11, null, 'Science');
INSERT INTO topics (id, topic_category, topic_name) VALUES (12, null, 'Car');
INSERT INTO topics (id, topic_category, topic_name) VALUES (13, null, 'Moto');
INSERT INTO topics (id, topic_category, topic_name) VALUES (14, null, 'Bitcoin');
INSERT INTO topics (id, topic_category, topic_name) VALUES (16, null, 'PC');
INSERT INTO topics (id, topic_category, topic_name) VALUES (17, 'GAMING', 'Game');
INSERT INTO topics (id, topic_category, topic_name) VALUES (18, 'GAMING', 'Cyberpunk 2077');
INSERT INTO topics (id, topic_category, topic_name) VALUES (19, 'ONLY_ON_TWITTER', 'Funny Tweets');
INSERT INTO topics (id, topic_category, topic_name) VALUES (20, 'ONLY_ON_TWITTER', 'Viral Tweets');
INSERT INTO topics (id, topic_category, topic_name) VALUES (21, 'ONLY_ON_TWITTER', 'Based on your searches');
INSERT INTO topics (id, topic_category, topic_name) VALUES (22, 'ONLY_ON_TWITTER', 'Spaces You Might Like');
INSERT INTO topics (id, topic_category, topic_name) VALUES (23, 'ONLY_ON_TWITTER', 'Popular images');
INSERT INTO topics (id, topic_category, topic_name) VALUES (24, 'ONLY_ON_TWITTER', 'Popular videos');
INSERT INTO topics (id, topic_category, topic_name) VALUES (25, 'ONLY_ON_TWITTER', 'Days of celebration');
INSERT INTO topics (id, topic_category, topic_name) VALUES (26, 'ONLY_ON_TWITTER', 'On this day');
INSERT INTO topics (id, topic_category, topic_name) VALUES (27, 'GAMING', 'Game development');
INSERT INTO topics (id, topic_category, topic_name) VALUES (28, 'GAMING', 'Call of Duty');
--rollback TRUNCATE topics

--changeset soroka andrei:2
INSERT INTO users(id, full_name, username, private_profile) VALUES (1, 'Andrei Soroka', 'Andrei Soroka', true);
INSERT INTO users(id, full_name, username, private_profile) VALUES (2, 'Androsor', 'Androsor', true);
INSERT INTO users(id, full_name, username, private_profile) VALUES (3, 'JavaDed', 'JavaDed', true);
INSERT INTO users(id, full_name, username, private_profile) VALUES (4, 'Пиздобол', 'Пиздобол', false);
INSERT INTO users(id, full_name, username, private_profile) VALUES (5, 'Хрен с горы', 'Хрен с горы', false);
--rollback TRUNCATE users

--changeset soroka andrei:3
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 2);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 3);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 4);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 5);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (2, 1);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (5, 1);
--rollback TRUNCATE user_subscriptions