-- images
INSERT INTO images (id, src) VALUES (10001, 'https://perfumeshop.s3.amazonaws.com/c0640bc6-38c6-40f6-97df-6d7975a931cf_User%2015.jpg');
INSERT INTO images (id, src) VALUES (10, 'https://perfumeshop.s3.amazonaws.com/c0640bc6-38c6-40f6-97df-6d7975a931cf_User%2015.jpg');

-- tags
INSERT INTO tags (id, tag_name, tweets_quantity) VALUES (10, '#tweet', 10);

-- users
INSERT INTO users (id, about, activation_code, active, birthday, email, full_name, location, password, password_reset_code, profile_customized, registration_date, role, tweet_count, username, website, avatar_id, wallpaper_id, profile_started) VALUES (10, 'Hello twitter!', null, true, 'Mar 10, 1991', 'androsor99@gmail.com', 'Vbhjckfd1', 'Minsk', '$2a$08$8g9Z3eos6ljEOLLoxJ8ZK.wjP7.fYaf7zMF54Uio44YjQJ4F27YcW', null, true, '2021-08-01 23:34:32.000000', 'USER', 8, 'Vbhjckfd1', 'http://localhost:3000/user/1', 10001, 10001, true);
INSERT INTO users (id, about, activation_code, active, birthday, email, full_name, location, password, password_reset_code, profile_customized, registration_date, role, tweet_count, username, website, avatar_id, wallpaper_id, profile_started) VALUES (20, 'Hello twitter!', null, true, null, 'androsor992@gmail.com', 'Vbhjckfd2', 'Minsk', '$2a$08$T1SpeJPoOfEDpMdhPKMp.elE6XBXIGN2wNMuDNh0vNnsOice4K4cu', null, true, '2021-08-01 23:34:32.000000', 'USER', 3, 'Vbhjckfd2', 'http://localhost:3000/user/1', 10001, 10001, true);
INSERT INTO users (id, about, activation_code, active, birthday, email, full_name, location, password, password_reset_code, profile_customized, registration_date, role, tweet_count, username, website, avatar_id, wallpaper_id, profile_started) VALUES (30, 'Hello twitter!', null, true, null, 'androsor993@gmail.com', 'Vbhjckfd3', 'Minsk', '$2a$08$T1SpeJPoOfEDpMdhPKMp.elE6XBXIGN2wNMuDNh0vNnsOice4K4cu', null, true, '2021-08-01 23:34:32.000000', 'USER', null, 'Vbhjckfd3', 'http://localhost:3000/user/1', null, null, false);
INSERT INTO users (id, about, activation_code, active, birthday, email, full_name, location, password, password_reset_code, profile_customized, registration_date, role, tweet_count, username, website, avatar_id, wallpaper_id, profile_started) VALUES (40, 'Hello twitter!', null, true, null, 'androsor994@gmail.com', 'Vbhjckfd4', 'Minsk', '$2a$08$T1SpeJPoOfEDpMdhPKMp.elE6XBXIGN2wNMuDNh0vNnsOice4K4cu', null, true, '2021-08-01 23:34:32.000000', 'USER', null, 'Vbhjckfd4', 'http://localhost:3000/user/1', null, null, false);
INSERT INTO users (id, about, activation_code, active, birthday, email, full_name, location, password, password_reset_code, profile_customized, registration_date, role, tweet_count, username, website, avatar_id, wallpaper_id, profile_started) VALUES (50, 'Hello twitter!', null, true, null, 'androsor995@gmail.com', 'Vbhjckfd5', 'Minsk', '$2a$08$T1SpeJPoOfEDpMdhPKMp.elE6XBXIGN2wNMuDNh0vNnsOice4K4cu', null, true, '2021-08-01 23:34:32.000000', 'USER', null, 'Vbhjckfd5', 'http://localhost:3000/user/1', null, null, false);
INSERT INTO users (id, about, activation_code, active, birthday, email, full_name, location, password, password_reset_code, profile_customized, registration_date, role, tweet_count, username, website, avatar_id, wallpaper_id, profile_started) VALUES (60, 'Describe yourself', null, true, 'Mar 10, 1991', 'test@test.com', 'John Doe', null, '$2a$08$KrYFTgVjb94gG1HkcC9GY.Iw2xUK50DyrXY3nM6YHKF9Tit3bhQAi', null, true, '2021-08-01 23:34:32.000000', 'USER', 5, 'John Doe', null, 10001, 10001, true);
INSERT INTO users (id, about, activation_code, active, birthday, email, full_name, location, password, password_reset_code, profile_customized, registration_date, role, tweet_count, username, website, avatar_id, wallpaper_id, profile_started) VALUES (70, 'test', '1234567890', false, 'Mar 10, 1991', 'test1@test.test', 'John Doe', null, '$2a$08$KrYFTgVjb94gG1HkcC9GY.Iw2xUK50DyrXY3nM6YHKF9Tit3bhQAi', null, true, '2021-08-01 23:34:32.000000', 'USER', 5, 'John Doe', null, 10001, 10001, true);

-- tweets
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (10, null, null, '2021-08-09 00:09:35.000000', 'first tweet', 60);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (20, null, null, '2021-08-09 19:36:41.000000', 'tweet (1)', 60);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (30, null, null, '2021-08-09 19:37:58.000000', 'tweet (2)', 60);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (40, null, null, '2021-08-09 19:40:07.000000', 'tweet (3)', 60);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (50, null, null, '2021-08-09 20:32:54.000000', 'my tweet', 10);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (60, null, null, '2021-08-09 22:14:28.000000', 'my tweet 2', 10);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (70, null, null, '2021-08-10 21:27:36.000000', 'tweet tweet tweet tweet :smile_cat:', 10);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (80, null, 'John Doe', '2021-08-11 23:15:57.000000', 'hello world', 10);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (90, null, null, '2021-08-11 23:36:20.000000', 'test #tweet', 60);
INSERT INTO tweets (id, addressed_id, addressed_username, date_time, text, user_id) VALUES (100, null, 'John Doe', '2021-08-11 23:37:19.000000', 'reply test', 20);

-- retweets
INSERT INTO retweets (id, retweet_date, tweets_id, users_id) VALUES (10, '2021-08-09 19:42:32.000000', 40, 10);
INSERT INTO retweets (id, retweet_date, tweets_id, users_id) VALUES (30, '2021-08-09 20:33:07.000000', 20, 10);
INSERT INTO retweets (id, retweet_date, tweets_id, users_id) VALUES (40, '2021-08-09 20:33:08.000000', 30, 10);

-- like_tweets
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (60, '2021-08-09 20:16:36.000000', 40, 10);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (70, '2021-08-09 23:00:52.000000', 10, 10);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (80, '2021-08-09 23:01:02.000000', 60, 10);
INSERT INTO like_tweets (id, like_tweet_date, tweets_id, users_id) VALUES (90, '2021-08-11 01:20:47.000000', 20, 10);

-- user_subscriptions
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (60, 20);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (20, 60);

-- users_tweets
INSERT INTO users_tweets (user_id, tweets_id) VALUES (10, 70);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (10, 60);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (10, 50);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (10, 80);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (60, 40);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (60, 30);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (60, 20);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (60, 10);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (60, 90);
INSERT INTO users_tweets (user_id, tweets_id) VALUES (20, 100);

-- tweets_tags
INSERT INTO tweets_tags (tags_id, tweets_id) VALUES (10, 90);

-- tweets_images
INSERT INTO tweets_images (tweet_id, images_id) VALUES (90, 10);

-- replies
INSERT INTO replies (tweets_id, reply_id) VALUES (40, 80);
INSERT INTO replies (tweets_id, reply_id) VALUES (90, 100);
