-- users
INSERT INTO users (id, full_name, username, private_profile) VALUES (1, 'Андрей ', 'Андрей', false);
INSERT INTO users (id, full_name, username, private_profile) VALUES (2, 'Androsor99', 'Androsor99', false);
INSERT INTO users (id, full_name, username, private_profile) VALUES (3, 'Androsor99', 'Androsor99', true);
INSERT INTO users (id, full_name, username, private_profile) VALUES (4, 'Androsor99', 'Androsor99', true);
INSERT INTO users (id, full_name, username, private_profile) VALUES (5, 'Androsor99', 'Androsor99', true);
INSERT INTO users (id, full_name, username, private_profile) VALUES (6, 'Androsor99', 'Androsor99', false);
INSERT INTO users (id, full_name, username, private_profile) VALUES (7, 'Androsor99', 'Androsor99', false);

-- user_subscriptions
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 2);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (2, 1);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (4, 2);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (4, 1);

-- user_blocked
INSERT INTO user_blocked (user_id, blocked_user_id) VALUES (2, 4);
INSERT INTO user_blocked (user_id, blocked_user_id) VALUES (5, 2);
INSERT INTO user_blocked (user_id, blocked_user_id) VALUES (6, 2);

-- topics
INSERT INTO topics (id, topic_category, topic_name) VALUES (51, null, 'My Blog');
INSERT INTO topics (id, topic_category, topic_name) VALUES (52, null, 'Technology');
INSERT INTO topics (id, topic_category, topic_name) VALUES (53, null, 'Java development');
INSERT INTO topics (id, topic_category, topic_name) VALUES (54, null, 'Entertainment');
INSERT INTO topics (id, topic_category, topic_name) VALUES (55, null, 'OnlyFans');
INSERT INTO topics (id, topic_category, topic_name) VALUES (56, null, 'Денис Матвеенко');
INSERT INTO topics (id, topic_category, topic_name) VALUES (57, 'GAMING', 'Witcher');
INSERT INTO topics (id, topic_category, topic_name) VALUES (58, 'GAMING', 'Minecraft');
INSERT INTO topics (id, topic_category, topic_name) VALUES (59, 'GAMING', 'Dead Space');
INSERT INTO topics (id, topic_category, topic_name) VALUES (60, 'GAMING', 'S.T.A.L.K.E.R.');
INSERT INTO topics (id, topic_category, topic_name) VALUES (61, null, 'Science');
INSERT INTO topics (id, topic_category, topic_name) VALUES (62, null, 'Car');
INSERT INTO topics (id, topic_category, topic_name) VALUES (63, null, 'Moto');
INSERT INTO topics (id, topic_category, topic_name) VALUES (64, null, 'Bitcoin');
INSERT INTO topics (id, topic_category, topic_name) VALUES (66, null, 'PC');
INSERT INTO topics (id, topic_category, topic_name) VALUES (67, 'GAMING', 'Game');
INSERT INTO topics (id, topic_category, topic_name) VALUES (68, 'GAMING', 'Cyberpunk 2077');
INSERT INTO topics (id, topic_category, topic_name) VALUES (69, 'ONLY_ON_TWITTER', 'Funny Tweets');
INSERT INTO topics (id, topic_category, topic_name) VALUES (70, 'ONLY_ON_TWITTER', 'Viral Tweets');
INSERT INTO topics (id, topic_category, topic_name) VALUES (71, 'ONLY_ON_TWITTER', 'Based on your searches');
INSERT INTO topics (id, topic_category, topic_name) VALUES (72, 'ONLY_ON_TWITTER', 'Spaces You Might Like');
INSERT INTO topics (id, topic_category, topic_name) VALUES (73, 'ONLY_ON_TWITTER', 'Popular images');
INSERT INTO topics (id, topic_category, topic_name) VALUES (74, 'ONLY_ON_TWITTER', 'Popular videos');
INSERT INTO topics (id, topic_category, topic_name) VALUES (75, 'ONLY_ON_TWITTER', 'Days of celebration');
INSERT INTO topics (id, topic_category, topic_name) VALUES (76, 'ONLY_ON_TWITTER', 'On this day');
INSERT INTO topics (id, topic_category, topic_name) VALUES (77, 'GAMING', 'Game development');
INSERT INTO topics (id, topic_category, topic_name) VALUES (78, 'GAMING', 'Call of Duty');

-- topic_followers
INSERT INTO topic_followers (id, topic_id, user_id) VALUES (58, 2);
INSERT INTO topic_followers (id, topic_id, user_id) VALUES (67, 2);
INSERT INTO topic_followers (id, topic_id, user_id) VALUES (63, 2);
INSERT INTO topic_followers (id, topic_id, user_id) VALUES (62, 2);

-- topic_not_interested
INSERT INTO topic_not_interested (id, topic_id, user_id) VALUES (58, 2);
INSERT INTO topic_not_interested (id, topic_id, user_id) VALUES (69, 2);
