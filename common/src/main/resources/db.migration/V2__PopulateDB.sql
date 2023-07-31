-- images
INSERT INTO images (id, src)
VALUES (11, 'https://perfumeshop.s3.amazonaws.com/fc49edf3-809c-4486-ba3f-60a703d74e0f_User%2018.jpg');
INSERT INTO images (id, src)
VALUES (22, 'https://perfumeshop.s3.amazonaws.com/f9dab1bf-c140-4c15-8b78-c288e6501344_user2.jpg');
INSERT INTO images (id, src)
VALUES (33, 'https://perfumeshop.s3.amazonaws.com/f96536a6-3505-4601-8963-a4500a2d6938_user6.jpg');
INSERT INTO images (id, src)
VALUES (44, 'https://perfumeshop.s3.amazonaws.com/f4019112-e6b4-491b-8827-49a9a4df3c0f_User%2015.jpg');
INSERT INTO images (id, src)
VALUES (1, 'https://perfumeshop.s3.amazonaws.com/d4715945-8692-4aac-8439-6a91c3f9c553_user7.jpg');
INSERT INTO images (id, src)
VALUES (2, 'https://perfumeshop.s3.amazonaws.com/d239c5e7-9dcd-45d2-8699-b42f710aba25_User%2017.jpg');
INSERT INTO images (id, src)
VALUES (3, 'https://perfumeshop.s3.amazonaws.com/d239c5e7-9dcd-45d2-8699-b42f710aba25_User%2017.jpg');
INSERT INTO images (id, src)
VALUES (4, 'https://perfumeshop.s3.amazonaws.com/b6c53b88-be4c-4b6c-ab6c-09eb0c205cc9_User%2004.jpg');
INSERT INTO images (id, src)
VALUES (5, 'https://perfumeshop.s3.amazonaws.com/5eeed7cc-b31b-4dd8-89f3-99bdb38c75f8_User%2001.jpg');
INSERT INTO images (id, src)
VALUES (6, 'https://perfumeshop.s3.amazonaws.com/5eeed7cc-b31b-4dd8-89f3-99bdb38c75f8_User%2001.jpg');
INSERT INTO images (id, src)
VALUES (7, 'https://perfumeshop.s3.amazonaws.com/4c6e92ad-22f3-4a20-be13-f79ffe890694_User%2012.jpg');
INSERT INTO images (id, src)
VALUES (8, 'https://perfumeshop.s3.amazonaws.com/4c6e92ad-22f3-4a20-be13-f79ffe890694_User%2012.jpg');
-- users
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website) VALUES (1, null, null, true, 'DEFAULT', null, 'BLUE', null, null, 'user2015@gmail.com', 'Random', null, null, 1, null, 0, true, 0, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2021-11-15 14:05:08.000000', 'USER', 0, 'Random', null);
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website) VALUES (2, 'Hello twitter!', null, true, 'DIM', null, 'BLUE', 'UA', 'UA', 'user2016@gmail.com', 'MrCat', 'Cat', 'Ukrainian - українська', 30, 'New York', 22, false, 0, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, 666966623, true, false, true, '2021-08-01 23:34:32.000000', 'USER', 4, 'Cat', 'https://www.google.com');
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website) VALUES (3, 'Hello twitter!', null, true, 'DEFAULT', null, 'BLUE', null, null, 'user2017@gmail.com', 'Kitty', null, null, 0, 'New York', 0, true, 2, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2021-08-01 23:34:32.000000', 'USER', 0, 'Kitty', 'https://www.google.com');
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website) VALUES (4, 'Hello twitter!', null, true, 'DEFAULT', null, 'BLUE', null, null, 'user2019@gmail.com', 'JavaCat', null, null, 0, 'Java', 0, false, 1, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2021-08-01 23:34:32.000000', 'USER', 0, 'JavaCat', 'https://www.java.com');
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website) VALUES (5, 'Hello twitter!', null, true, 'DEFAULT', null, 'BLUE', null, null, 'user2018@gmail.com', 'КотБегемот', null, null, 0, 'London', 0, false, 2, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2021-08-01 23:34:32.000000', null, 0, 'Кот Бегемот', 'https://www.google.com');
-- tweets
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (1, null, null, null, '2021-10-15 21:20:15.000000', null, null, null, null, null, 'EVERYONE', null, 'My #FirstTweet :slightly_smiling_face:', 1, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (7, null, null, null, '2021-10-15 21:20:27.000000', null, null, null, null, null, 'EVERYONE', null, 'Hello #FirstTweet  :sunglasses:', 3, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (13, null, null, null, '2021-10-15 21:57:21.000000', null, null, null, null, null, 'EVERYONE', null, '#JavaScript', 4, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (10, null, null, null, '2021-10-15 21:21:48.000000', null, null, null, null, null, 'EVERYONE', null, '#myCat  :kissing_cat:', 4, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (12, null, null, null, '2021-10-15 21:23:41.000000', null, null, null, null, null, 'MENTION', null, '#myCat  :smiley_cat:', 5, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (11, null, null, null, '2021-10-15 21:22:23.000000', null, null, null, null, null, 'EVERYONE', null, 'My #FirstTweet', 5, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (16, null, 10, 'JavaCat', '2021-10-15 22:21:30.000000', null, null, null, null, null, 'EVERYONE', null, 'Feels good man  :sunglasses:', 2, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (8, null, null, null, '2021-10-15 21:20:30.000000', null, null, null, null, null, 'FOLLOW', null, '#myCat  :smiley_cat:', 3, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (9, null, null, null, '2021-10-15 21:20:33.000000', null, null, null, null, null, 'EVERYONE', null, '#FirstTweet', 4, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (6, null, null, null, '2021-10-15 21:20:26.000000', null, null, null, null, null, 'EVERYONE', null, '#myCat  :smile_cat:', 2, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (5, null, null, null, '2021-10-15 21:20:24.000000', null, null, null, null, null, 'EVERYONE', null, 'Another #FirstTweet', 2, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (15, null, null, null, '2021-10-15 22:11:59.000000', null, null, null, null, null, 'EVERYONE', null, 'Hello :wave:', 1, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (14, null, null, null, '2021-10-15 22:10:14.000000', null, null, null, null, null, 'MENTION', null, 'Feels Good Man  :sunglasses:', 1, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (4, null, null, null, '2021-10-15 21:20:22.000000', 'https://www.youtube.com/watch?v=hTWKbfoikeg&ab_channel=NirvanaVEVO', 'https://i.ytimg.com/vi/hTWKbfoikeg/mqdefault.jpg', null, null, 'Nirvana - Smells Like Teen Spirit (Official Music Video)', 'EVERYONE', null, 'https://www.youtube.com/watch?v=hTWKbfoikeg&ab_channel=NirvanaVEVO', 1, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (3, null, null, null, '2021-10-15 21:20:21.000000', 'https://www.youtube.com/watch?v=-k9qDxyxS3s&ab_channel=BMTHOfficialVEVO', 'https://i.ytimg.com/vi/-k9qDxyxS3s/mqdefault.jpg', null, null, 'Bring Me The Horizon - Shadow Moses (Official Video)', 'EVERYONE', null, 'https://www.youtube.com/watch?v=-k9qDxyxS3s&ab_channel=BMTHOfficialVEVO', 1, false);
INSERT INTO tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, link, link_cover, link_cover_size, link_description, link_title, reply_type, scheduled_date, text, users_id, deleted) VALUES (2, null, null, null, '2021-10-15 21:20:18.000000', 'https://www.youtube.com/watch?v=ewZZNeYDiLo&ab_channel=TeamSESH', 'https://i.ytimg.com/vi/ewZZNeYDiLo/mqdefault.jpg', null, null, 'Bones - RestInPeace', 'EVERYONE', null, 'https://www.youtube.com/watch?v=ewZZNeYDiLo&ab_channel=TeamSESH', 1, false);
-- users_tweets
INSERT INTO users_tweets (user_id, tweets_id) VALUES (5, 12);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (4, 10);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (4, 9);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (3, 7);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (5, 11);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (3, 8);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (1, 2);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (1, 4);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (1, 1);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (1, 3);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (1, 14);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (1, 15);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (2, 6);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (2, 5);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (2, 13);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (2, 16);
-- user_avatar
INSERT INTO user_avatar (avatar_id, user_id) VALUES (11, 1);
INSERT INTO user_avatar (avatar_id, user_id) VALUES (33, 2);
INSERT INTO user_avatar (avatar_id, user_id) VALUES (3, 3);
INSERT INTO user_avatar (avatar_id, user_id) VALUES (5, 4);
INSERT INTO user_avatar (avatar_id, user_id) VALUES (7, 5);
-- user_wallpaper
INSERT INTO user_wallpaper (wallpaper_id, user_id) VALUES (22, 1);
INSERT INTO user_wallpaper (wallpaper_id, user_id) VALUES (44, 2);
-- tweet_quote
INSERT INTO tweet_quote (quote_tweet_id, tweets_id) VALUES (13, 15);
-- tweets_images
INSERT INTO tweets_images (tweet_id, images_id) VALUES (6, 1);
INSERT INTO tweets_images (tweet_id, images_id) VALUES (8, 4);
INSERT INTO tweets_images (tweet_id, images_id) VALUES (10, 6);
INSERT INTO tweets_images (tweet_id, images_id) VALUES (12, 8);
-- like_tweets
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (1, '2021-10-15 21:36:52.000000', 1, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (2, '2021-10-15 21:37:01.000000', 2, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (3, '2021-10-15 21:37:03.000000', 3, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (4, '2021-10-15 21:37:05.000000', 4, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (5, '2021-10-15 21:37:06.000000', 5, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (6, '2021-10-15 21:37:08.000000', 6, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (7, '2021-10-15 21:37:10.000000', 7, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (8, '2021-10-15 21:37:11.000000', 8, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (9, '2021-10-15 21:37:14.000000', 9, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (10, '2021-10-15 21:37:15.000000', 10, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (11, '2021-10-15 21:37:17.000000', 11, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (12, '2021-10-15 21:37:18.000000', 12, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (13, '2021-10-15 21:43:36.000000', 5, 1);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (14, '2021-10-15 21:43:39.000000', 6, 1);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (15, '2021-10-15 22:00:37.000000', 13, 2);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (16, '2021-10-15 22:08:53.000000', 1, 1);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (17, '2021-10-15 22:39:04.000000', 8, 1);
-- retweets
INSERT INTO retweets (id, retweet_date, tweets_id, users_id) VALUES (1, '2021-10-15 21:50:56.000000', 10, 1);
INSERT INTO retweets (id, retweet_date, tweets_id, users_id) VALUES (2, '2021-10-15 22:00:41.000000', 13, 2);
INSERT INTO retweets (id, retweet_date, tweets_id, users_id) VALUES (3, '2021-10-15 22:07:48.000000', 10, 2);
-- replies
INSERT INTO replies (tweets_id, reply_id) VALUES (10, 16);
-- quotes
INSERT INTO quotes (tweets_id, quote_id) VALUES (13, 15);
-- user_pinned_tweet
INSERT INTO user_pinned_tweet (tweet_id, user_id) VALUES (1, 1);
-- tags
INSERT INTO tags (id, tag_name, tweets_quantity) VALUES (1, '#FirstTweet', 5);
INSERT INTO tags (id, tag_name, tweets_quantity) VALUES (2, '#myCat', 4);
INSERT INTO tags (id, tag_name, tweets_quantity) VALUES (3, '#JavaScript', 1);
-- tweets_tags
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (1, 1);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (1, 5);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (1, 7);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (1, 9);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (1, 11);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (2, 6);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (2, 8);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (2, 10);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (2, 12);
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (3, 13);
-- bookmarks
INSERT INTO bookmarks (id, bookmark_date, tweet_id, users_id) VALUES (1, '2022-02-10 21:09:18.000000', 8, 1);
INSERT INTO bookmarks (id, bookmark_date, tweet_id, users_id) VALUES (2, '2022-02-10 21:09:28.000000', 15, 1);
-- chats
INSERT INTO chats (id, creation_date) VALUES (1, '2021-10-16 16:40:07.000000');
-- chats_participants
INSERT INTO chats_participants (id, left_chat, chat_id, user_id) VALUES (1, false, 1, 1);
INSERT INTO chats_participants (id, left_chat, chat_id, user_id) VALUES (2, false, 1, 2);
-- chat_messages
INSERT INTO chat_messages (id, date, text, user_id, chat_id, tweet_id) VALUES (1, '2021-10-16 16:40:07.000000', 'Hello Cat', 1, 1, null);
INSERT INTO chat_messages (id, date, text, user_id, chat_id, tweet_id) VALUES (2, '2021-10-16 16:40:41.000000', 'How are you?', 1, 1, null);
INSERT INTO chat_messages (id, date, text, user_id, chat_id, tweet_id) VALUES (3, '2021-10-16 16:41:59.000000', 'I''m fine, thanks, and you? ', 2, 1, null);
INSERT INTO chat_messages (id, date, text, user_id, chat_id, tweet_id) VALUES (4, '2021-10-16 16:42:50.000000', 'Good)', 1, 1, 14);
-- unread_messages
INSERT INTO unread_messages (user_id, chat_message_id) VALUES (2, 4);
-- lists
INSERT INTO lists (id, alt_wallpaper, description, private, name, user_id, wallpaper_id) VALUES (3, 'https://pbs.twimg.com/media/EXZ27UwVcAIcDfd?format=png&name=small', 'Hello from my list', false, 'Hello World!', 2, null);
INSERT INTO lists (id, alt_wallpaper, description, private, name, user_id, wallpaper_id) VALUES (2, 'https://pbs.twimg.com/media/EXZ2w_qUcAMwN3x?format=png&name=small', 'Some description', false, 'Internal', 1, null);
INSERT INTO lists (id, alt_wallpaper, description, private, name, user_id, wallpaper_id) VALUES (1, 'https://pbs.twimg.com/media/EXZ1_hkUYAA56JA?format=png&name=small', 'Random List Description', false, 'Random List', 1, null);
-- lists_members
INSERT INTO lists_members (lists_id, members_id) VALUES (1, 4);
INSERT INTO lists_members (lists_id, members_id) VALUES (1, 2);
-- notifications
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (1, '2021-10-15 21:36:52.000000', 'LIKE', 1, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (2, '2021-10-15 21:37:01.000000', 'LIKE', 2, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (3, '2021-10-15 21:37:03.000000', 'LIKE', 3, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (4, '2021-10-15 21:37:05.000000', 'LIKE', 4, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (5, '2021-10-15 21:37:10.000000', 'LIKE', 7, 2, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (6, '2021-10-15 21:37:11.000000', 'LIKE', 8, 2, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (7, '2021-10-15 21:37:14.000000', 'LIKE', 9, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (8, '2021-10-15 21:37:15.000000', 'LIKE', 10, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (9, '2021-10-15 21:37:17.000000', 'LIKE', 11, 2, null, null, 5);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (10, '2021-10-15 21:37:18.000000', 'LIKE', 12, 2, null, null, 5);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (11, '2021-10-15 21:43:36.000000', 'LIKE', 5, 1, null, null, 2);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (12, '2021-10-15 21:43:39.000000', 'LIKE', 6, 1, null, null, 2);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (13, '2021-10-15 21:43:52.000000', 'FOLLOW', null, 1, 2, null, 2);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (14, '2021-10-15 21:50:56.000000', 'RETWEET', 10, 1, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (15, '2021-10-15 22:00:37.000000', 'LIKE', 13, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (16, '2021-10-15 22:00:41.000000', 'RETWEET', 13, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (17, '2021-10-15 22:01:19.000000', 'FOLLOW', null, 2, 1, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (18, '2021-10-15 22:07:48.000000', 'RETWEET', 10, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (19, '2021-10-15 22:10:37.000000', 'RETWEET', 13, 1, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (20, '2021-10-15 22:37:51.000000', 'RETWEET', 8, 1, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (21, '2021-10-15 22:39:04.000000', 'LIKE', 8, 1, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (22, '2021-10-15 22:45:53.000000', 'FOLLOW', null, 1, 5, null, 5);
-- user_subscriptions
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 2);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 3);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 4);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 5);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (2, 1);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (5, 1);
-- users_notifications
INSERT INTO users_notifications (user_id, notifications_id) VALUES (2, 13);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (2, 12);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (2, 11);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (5, 10);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (5, 9);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (5, 22);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (4, 18);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (4, 16);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (4, 15);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (4, 14);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (4, 8);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (4, 7);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (4, 19);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (3, 5);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (3, 6);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (3, 20);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (3, 21);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (1, 17);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (1, 4);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (1, 3);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (1, 2);
INSERT INTO users_notifications (user_id, notifications_id) VALUES (1, 1);
-- topics
INSERT INTO topics (id, topic_category, topic_name) VALUES (1001, null, 'Elon Musk');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1002, null, 'Technology');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1003, null, 'Web development');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1004, null, 'Entertainment');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1005, null, 'Digital creators');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1006, null, 'Kanye West');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1007, 'GAMING', 'Animal Crossing');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1008, 'GAMING', 'Minecraft');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1009, 'GAMING', 'MrBeast');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1010, 'GAMING', 'PewDiePie');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1011, null, 'Science');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1012, null, 'Cats');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1013, null, 'Dogs');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1014, null, 'Bitcoin');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1015, null, 'Science');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1016, null, 'Xbox');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1017, 'GAMING', 'Game');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1018, 'GAMING', 'Cyberpunk 2077');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1019, 'ONLY_ON_TWITTER', 'Funny Tweets');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1020, 'ONLY_ON_TWITTER', 'Viral Tweets');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1021, 'ONLY_ON_TWITTER', 'Based on your searches');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1022, 'ONLY_ON_TWITTER', 'Spaces You Might Like');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1023, 'ONLY_ON_TWITTER', 'Popular images');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1024, 'ONLY_ON_TWITTER', 'Popular videos');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1025, 'ONLY_ON_TWITTER', 'Days of celebration');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1026, 'ONLY_ON_TWITTER', 'On this day');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1027, 'GAMING', 'Game development');
INSERT INTO topics (id, topic_category, topic_name) VALUES (1028, 'GAMING', 'Among Us');