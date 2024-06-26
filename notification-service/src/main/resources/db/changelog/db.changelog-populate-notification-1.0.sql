-- liquibase formatted sql

--changeset soroka andrei:1
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (1, '2023-09-04 21:36:52.000000', 'LIKE', 1, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (2, '2023-09-04 21:37:01.000000', 'LIKE', 2, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (3, '2023-09-04 21:37:03.000000', 'LIKE', 3, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (4, '2023-09-04 21:37:05.000000', 'LIKE', 4, 2, null, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (5, '2023-09-04 21:37:10.000000', 'LIKE', 7, 2, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (6, '2023-09-04 21:37:11.000000', 'LIKE', 8, 2, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (7, '2023-09-04 21:37:14.000000', 'LIKE', 9, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (8, '2023-09-04 21:37:15.000000', 'LIKE', 10, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (9, '2023-09-04 21:37:17.000000', 'LIKE', 11, 2, null, null, 5);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (10, '2023-09-04 21:37:18.000000', 'LIKE', 12, 2, null, null, 5);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (11, '2023-09-04 21:43:36.000000', 'LIKE', 5, 1, null, null, 2);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (12, '2023-09-04 21:43:39.000000', 'LIKE', 6, 1, null, null, 2);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (13, '2023-09-04 21:43:52.000000', 'FOLLOW', null, 1, 2, null, 2);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (14, '2023-09-04 21:50:56.000000', 'RETWEET', 10, 1, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (15, '2023-09-04 22:00:37.000000', 'LIKE', 13, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (16, '2023-09-04 22:00:41.000000', 'RETWEET', 13, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (17, '2023-09-04 22:01:19.000000', 'FOLLOW', null, 2, 1, null, 1);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (18, '2023-09-04 22:07:48.000000', 'RETWEET', 10, 2, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (19, '2023-09-04 22:10:37.000000', 'RETWEET', 13, 1, null, null, 4);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (20, '2023-09-04 22:37:51.000000', 'RETWEET', 8, 1, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (21, '2023-09-04 22:39:04.000000', 'LIKE', 8, 1, null, null, 3);
INSERT INTO notifications (id, date, notification_type, tweet_id, user_id, user_to_follow_id, list_id, notified_user_id) VALUES (22, '2023-09-04 22:45:53.000000', 'FOLLOW', null, 1, 5, null, 5);
--rollback TRUNCATE notifications
