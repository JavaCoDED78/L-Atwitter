-- tweet_images
INSERT INTO "test".public.tweet_images (id, src) VALUES (1, 'https://perfumeshop.s3.amazonaws.com/5eeed7cc-b31b-4dd8-89f3-99bdb38c75f8_User%2001.jpg');

-- polls
INSERT INTO "test".public.polls (id, date_time) VALUES (2, '2222-10-10 20:29:03.812910');
INSERT INTO "test".public.polls (id, date_time) VALUES (8, '2023-09-20 20:29:03.812910');

-- tweets
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (39, null, null, null, '2023-09-20 20:29:03.00vo0000', 'EVERYONE', 'test tweet',  2, null, null, null, null, null, '3021-10-03 20:33:36.000000', false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (40, null, null, null, '2023-09-20 20:29:03.00vo0000', 'EVERYONE', 'test tweet',  2, null, null, null, null, null, null, false, 2, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (41, 2, 40, 'Androsor', '2023-09-20 20:31:55.000000', 'EVERYONE', 'test reply', 1, null, null, null, null, null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (42, null, null, null, '2023-09-20 20:33:36.000000', 'EVERYONE', 'https://www.youtube.com/watch?v=wd29NuVLjWU&t=36s', 2, 'https://www.youtube.com/watch?v=wd29NuVLjWU&t=36s', 'https://i.ytimg.com/vi/wd29NuVLjWU/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLBdqVPo2tVURiz2KTdEMrf7FG-6Zg.jpg', null, 'Klim Zhukov. Midshipmen back, it''s such a shame', null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (43, null, null, null, '2023-09-20 20:34:15.000000', 'EVERYONE', '#JetBrains https://www.jetbrains.com/ ', 2, 'https://www.jetbrains.com/', 'https://resources.jetbrains.com/storage/products/jetbrains/img/meta/preview.png', 'JetBrains is a cutting-edge software vendor specializing in the creation of intelligent development tools, including IntelliJ IDEA - the leading Java IDE, and the Kotlin programming language.', 'JetBrains', 'LARGE', null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (44, null, null, null, '2023-09-20 20:36:25.000000', 'EVERYONE', 'test quote', 2, null, null, null, null, null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (45, null, null, null, '2023-09-20 20:38:51.000000', 'EVERYONE', 'media tweet test', 1, null, null, null, null, null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (46, null, null, null, '2023-09-20 20:38:51.000000', 'EVERYONE', 'hello world1', 2, null, null, null, null, null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (47, null, null, null, '2023-09-20 20:39:51.000000', 'EVERYONE', 'hello world2', 2, null, null, null, null, null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (48, null, null, null, '2023-09-20 20:40:51.000000', 'EVERYONE', 'hello world3', 2, null, null, null, null, null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (49, null, null, null, '2023-09-20 20:40:51.000000', 'EVERYONE', 'hello world3', 2, null, null, null, null, null, null, true, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (50, null, null, null, '2023-09-20 20:40:51.000000', 'EVERYONE', 'hello world3', 3, null, null, null, null, null, null, false, null, null, null, null);
INSERT INTO "test".public.tweets (id, addressed_id, addressed_tweet_id, addressed_username, date_time, reply_type, text, author_id, link, link_cover, link_description, link_title, link_cover_size, scheduled_date, deleted, poll_id, list_id, image_description, gif_image_id) VALUES (51, null, null, null, '2023-09-20 20:40:51.000000', 'EVERYONE', 'hello world3', 6, null, null, null, null, null, null, false, null, null, null, null);

-- tagged_image_users
INSERT INTO "test".public.tagged_image_users (tweet_id, tagged_image_user_id) VALUES (45, 1);
INSERT INTO "test".public.tagged_image_users (tweet_id, tagged_image_user_id) VALUES (45, 2);

-- tweet_quote
INSERT INTO "test".public.tweet_quote (tweet_id, quote_tweet_id) VALUES (44, 43);
INSERT INTO "test".public.tweet_quote (tweet_id, quote_tweet_id) VALUES (45, 40);

-- tweets_images
INSERT INTO "test".public.tweets_images (tweet_id, images_id) VALUES (45, 1);


-- liked_tweets (like_tweets)
INSERT INTO "test".public.liked_tweets (id, liked_tweet_date, tweet_id, user_id) VALUES (80, '2023-09-20 20:31:44.000000', 40, 1);
INSERT INTO "test".public.liked_tweets (id, liked_tweet_date, tweet_id, user_id) VALUES (81, '2023-09-20 20:31:44.000000', 45, 2);

-- retweets
INSERT INTO "test".public.retweets (id, retweet_date, tweet_id, user_id) VALUES (8, '2023-09-20 20:31:47.000000', 40, 1);
INSERT INTO "test".public.retweets (id, retweet_date, tweet_id, user_id) VALUES (9, '2023-09-20 20:31:47.000000', 45, 2);

-- replies
INSERT INTO "test".public.replies (tweet_id, reply_id) VALUES (40, 41);

-- quotes
INSERT INTO "test".public.quotes (tweet_id, quote_id) VALUES (44, 43);
INSERT INTO "test".public.quotes (tweet_id, quote_id) VALUES (45, 40);

-- poll_choices
INSERT INTO "test".public.poll_choices (id, choice) VALUES (9, 'test 1');
INSERT INTO "test".public.poll_choices (id, choice) VALUES (10, 'test 2');
INSERT INTO "test".public.poll_choices (id, choice) VALUES (11, 'test 3');
INSERT INTO "test".public.poll_choices (id, choice) VALUES (12, 'test 4');

-- polls_poll_choices
INSERT INTO "test".public.polls_poll_choices (poll_id, poll_choices_id) VALUES (2, 9);
INSERT INTO "test".public.polls_poll_choices (poll_id, poll_choices_id) VALUES (2, 10);
INSERT INTO "test".public.polls_poll_choices (poll_id, poll_choices_id) VALUES (8, 11);
INSERT INTO "test".public.polls_poll_choices (poll_id, poll_choices_id) VALUES (8, 12);

-- poll_choice_voted (pool_choices_voted_user)
INSERT INTO "test".public.poll_choice_voted (id, poll_choice_id, voted_user_id) VALUES (1, 10, 1);

-- bookmarks
INSERT INTO "test".public.bookmarks (id, bookmark_date, tweet_id, user_id) VALUES (2, '2023-09-20 20:35:53.000000', 40, 2);
