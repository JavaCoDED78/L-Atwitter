-- liquibase formatted sql

--changeset soroka andrei:1
INSERT INTO users(id, full_name, username, private_profile, active, about, avatar) VALUES (1, 'Andrei Soroka', 'Andrei Soroka', true, true, null, 'https://perfumeshop.s3.amazonaws.com/e10244ba-71fa-4c10-b885-33674ef0121f_user17.jpg');
INSERT INTO users(id, full_name, username, private_profile, active, about, avatar) VALUES (2, 'Androsor', 'Androsor', true, true, 'Hello twitter!', 'https://perfumeshop.s3.amazonaws.com/d3698982-06b3-4361-b3cf-3e795d4833a7_User%2004.jpg');
INSERT INTO users(id, full_name, username, private_profile, active, about, avatar) VALUES (3, 'JavaDed', 'JavaDed', true, true, 'Hello twitter!', 'https://perfumeshop.s3.amazonaws.com/4c6e92ad-22f3-4a20-be13-f79ffe890694_User%2012.jpg');
INSERT INTO users(id, full_name, username, private_profile, active, about, avatar) VALUES (4, 'Пиздобол', 'Пиздобол', false, true, 'Hello twitter!', 'https://perfumeshop.s3.amazonaws.com/7451f79f-dd52-48e3-b3d8-f2a1530ac20e_User%2014.jpg');
INSERT INTO users(id, full_name, username, private_profile, active, about, avatar) VALUES (5, 'Хрен с горы', 'Хрен с горы', false, true, 'Hello twitter!', 'https://perfumeshop.s3.amazonaws.com/6cecddce-91f2-41dd-ade0-87461706ccd6_user6.jpg');
--rollback TRUNCATE users

--changeset soroka andrei:2
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 2);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 3);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 4);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (1, 5);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (2, 1);
INSERT INTO user_subscriptions (subscriber_id, user_id) VALUES (5, 1);
--rollback TRUNCATE user_subscriptions

--changeset soroka andrei:3
INSERT INTO lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (1, 'https://pbs.twimg.com/media/EXZ1_hkUYAA56JA?format=png&name=small', 'Random List Description', false, 'Random List', 1, null);
INSERT INTO lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (2, 'https://pbs.twimg.com/media/EXZ2w_qUcAMwN3x?format=png&name=small', 'Some description', false, 'Internal', 1, null);
INSERT INTO lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (3, 'https://pbs.twimg.com/media/EXZ27UwVcAIcDfd?format=png&name=small', 'Hello from my list', false, 'Hello World!', 2, null);

-- lists_members
INSERT INTO lists_members (list_id, member_id) VALUES (1, 4);
INSERT INTO lists_members (list_id, member_id) VALUES (1, 2);




