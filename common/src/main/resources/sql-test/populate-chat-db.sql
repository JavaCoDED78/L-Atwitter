-- chats
INSERT INTO "test".public.chats (id, creation_date) VALUES (8, '2023-09-14 20:29:55.000000');
INSERT INTO "test".public.chats (id, creation_date) VALUES (10, '2023-09-14 20:29:55.000000');

-- chats_participants
INSERT INTO "test".public.chats_participants (id, left_chat, chat_id, user_id) VALUES (3, false, 8, 2);
INSERT INTO "test".public.chats_participants (id, left_chat, chat_id, user_id) VALUES (4, false, 8, 1);
INSERT INTO "test".public.chats_participants (id, left_chat, chat_id, user_id) VALUES (5, false, 10, 2);
INSERT INTO "test".public.chats_participants (id, left_chat, chat_id, user_id) VALUES (6, true, 10, 5);

-- chat_messages
INSERT INTO "test".public.chat_messages (id, date, text, author_id, chat_id, tweet_id, is_unread) VALUES (5, '2023-09-14 20:39:55.000000', 'hello from Androsor', 2, 8, 40, true);
INSERT INTO "test".public.chat_messages (id, date, text, author_id, chat_id, tweet_id, is_unread) VALUES (6, '2023-09-14 20:40:19.000000', 'hello from Andrei Soroka', 1, 8, null, false);
INSERT INTO "test".public.chat_messages (id, date, text, author_id, chat_id, tweet_id, is_unread) VALUES (7, '2023-09-14 20:41:03.000000', 'test message 2 from Andrei Soroka', 1, 8, null, true);
