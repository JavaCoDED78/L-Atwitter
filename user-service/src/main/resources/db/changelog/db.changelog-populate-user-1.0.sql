-- liquibase formatted sql

--changeset soroka andrei:1
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, mentions_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website, pinned_tweet_id, unread_messages_count, avatar, wallpaper) VALUES (1, 'Hello2', null, true, 'DEFAULT', null, 'BLUE', null, null, 'androsor2025@gmail.com', 'Andrei Soroka', null, null, 1, null, 0, true, 0, 0, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2023-09-21 14:05:08.000000', 'USER', 0, 'Andrei Soroka', null, 1, 0, 'https://perfumeshop.s3.amazonaws.com/e10244ba-71fa-4c10-b885-33674ef0121f_user17.jpg', 'https://perfumeweb2.s3.eu-central-1.amazonaws.com/dfc8a223-45fc-43da-8b7c-f76e2c2507cd_82ecbca14eb4999212c07257f41c70e7.jpg');
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, mentions_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website, pinned_tweet_id, unread_messages_count, avatar, wallpaper) VALUES (2, 'Hello twitter!', null, true, 'DIM', null, 'BLUE', 'RU', 'RU', 'androsor2026@gmail.com', 'Androsor', 'Androsor', 'Belarusian - беларуская', 30, 'Minsk', 22, false, 0, 0, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, 291406806, true, false, true, '2023-09-21 23:34:32.000000', 'USER', 4, 'Androsor', 'https://www.google.com', null, 0, 'https://perfumeshop.s3.amazonaws.com/d3698982-06b3-4361-b3cf-3e795d4833a7_User%2004.jpg', 'https://perfumeweb2.s3.eu-central-1.amazonaws.com/d0e5b95f-acc0-47ef-b499-477f7e5a1a06_PrMnWa2z.jpg');
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, mentions_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website, pinned_tweet_id, unread_messages_count, avatar, wallpaper) VALUES (3, 'Hello twitter!', null, true, 'DEFAULT', null, 'BLUE', null, null, 'androsor2027@gmail.com', 'JavaDed', null, null, 0, 'Grodno', 0, true, 2, 0, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2023-09-21 23:34:32.000000', 'USER', 0, 'JavaDed', 'https://www.google.com', null, 0, 'https://perfumeshop.s3.amazonaws.com/4c6e92ad-22f3-4a20-be13-f79ffe890694_User%2012.jpg', null);
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, mentions_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website, pinned_tweet_id, unread_messages_count, avatar, wallpaper) VALUES (4, 'Hello twitter!', null, true, 'DEFAULT', null, 'BLUE', null, null, 'androsor2029@gmail.com', 'Пиздобол', null, null, 0, 'Пуховичи', 0, false, 1, 0, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2023-09-21 23:34:32.000000', 'USER', 0, 'Пиздобол', 'https://www.java.com', null, 0, 'https://perfumeshop.s3.amazonaws.com/7451f79f-dd52-48e3-b3d8-f2a1530ac20e_User%2014.jpg', null);
INSERT INTO users (id, about, activation_code, active, background_color, birthday, color_scheme, country, country_code, email, full_name, gender, language, like_count, location, media_tweet_count, muted_direct_messages, notifications_count, mentions_count, password, password_reset_code, phone, private_profile, profile_customized, profile_started, registration_date, role, tweet_count, username, website, pinned_tweet_id, unread_messages_count, avatar, wallpaper) VALUES (5, 'Hello twitter!', null, true, 'DEFAULT', null, 'BLUE', null, null, 'androsor2028@gmail.com', 'Хрен с горы', null, null, 0, 'Мядель', 0, false, 2, 0, '$2a$08$TZekzxmq.KO2f.juYNUa4eU/ePYPx3r2MWONVjPDKOBJB4qUlhkxW', null, null, false, true, true, '2023-09-21 23:34:32.000000', 'USER', 0, 'Хрен с горы', 'https://www.google.com', null, 0, 'https://perfumeshop.s3.amazonaws.com/6cecddce-91f2-41dd-ade0-87461706ccd6_user6.jpg', null);
--rollback TRUNCATE users

--changeset soroka andrei:2
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 2);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 3);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 4);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 5);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (2, 1);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (5, 1);
--rollback TRUNCATE user_subscriptions
