-- liquibase formatted sql

--changeset soroka andrei:1
INSERT INTO chats (id, creation_date) VALUES (8, '2023-09-12 20:29:55.000000');
INSERT INTO chats (id, creation_date) VALUES (10, '2023-09-12 20:29:55.000000');
--rollback TRUNCATE chats

--changeset soroka andrei:2
INSERT INTO chats_participants (id, left_chat, chat_id, user_id) VALUES (3, false, 8, 2);
INSERT INTO chats_participants (id, left_chat, chat_id, user_id) VALUES (4, false, 8, 1);
INSERT INTO chats_participants (id, left_chat, chat_id, user_id) VALUES (5, false, 10, 2);
INSERT INTO chats_participants (id, left_chat, chat_id, user_id) VALUES (6, true, 10, 5);
--rollback TRUNCATE chat_participants

--changeset soroka andrei:3
INSERT INTO chat_messages (id, date, text, author_id, chat_id, tweet_id) VALUES (5, '2023-09-12 20:39:55.000000', 'hello from Androsor', 2, 8, 6);
INSERT INTO chat_messages (id, date, text, author_id, chat_id, tweet_id) VALUES (6, '2023-09-12 20:40:19.000000', 'hello from Andrei Soroka', 1, 8, null);
INSERT INTO chat_messages (id, date, text, author_id, chat_id, tweet_id) VALUES (7, '2023-09-12 20:41:03.000000', 'test message 2 from Andrei Soroka', 1, 8, null);
--rollback TRUNCATE chat_messages
