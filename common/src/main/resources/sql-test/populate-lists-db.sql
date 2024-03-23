-- users
INSERT INTO "test".public.users(id, full_name, username, private_profile, active, about, avatar) VALUES (1, 'John_Doe', 'John_Doe', false, true, 'Hello twitter!', 'https://twitterclonestorage.s3.eu-central-1.amazonaws.com/348b7dbe-3ac5-477f-8483-edc24f53091b_814370.jpg');
INSERT INTO "test".public.users(id, full_name, username, private_profile, active, about, avatar) VALUES (2, 'Andrei Soroka', 'Andrei Soroka', false, true, 'Hello twitter!', 'https://twitterclonestorage.s3.eu-central-1.amazonaws.com/348b7dbe-3ac5-477f-8483-edc24f53091b_814370.jpg');
INSERT INTO "test".public.users(id, full_name, username, private_profile, active, about, avatar) VALUES (3, 'Andrei Soroka', 'Andrei Soroka', true, true, 'Hello twitter!', 'https://twitterclonestorage.s3.eu-central-1.amazonaws.com/348b7dbe-3ac5-477f-8483-edc24f53091b_814370.jpg');
INSERT INTO "test".public.users(id, full_name, username, private_profile, active, about, avatar) VALUES (4, 'Andrei Soroka', 'Andrei Soroka', true, true, 'Hello twitter!', 'https://twitterclonestorage.s3.eu-central-1.amazonaws.com/348b7dbe-3ac5-477f-8483-edc24f53091b_814370.jpg');
INSERT INTO "test".public.users(id, full_name, username, private_profile, active, about, avatar) VALUES (5, 'Andrei Soroka', 'Andrei Soroka', true, true, 'Hello twitter!', 'https://twitterclonestorage.s3.eu-central-1.amazonaws.com/348b7dbe-3ac5-477f-8483-edc24f53091b_814370.jpg');
INSERT INTO "test".public.users(id, full_name, username, private_profile, active, about, avatar) VALUES (6, 'Andrei Soroka', 'Andrei Soroka', false, true, 'Hello twitter!', 'https://twitterclonestorage.s3.eu-central-1.amazonaws.com/348b7dbe-3ac5-477f-8483-edc24f53091b_814370.jpg');
INSERT INTO "test".public.users(id, full_name, username, private_profile, active, about, avatar) VALUES (7, 'Andrei Soroka', 'Andrei Soroka', false, true, 'Hello twitter!', 'https://twitterclonestorage.s3.eu-central-1.amazonaws.com/348b7dbe-3ac5-477f-8483-edc24f53091b_814370.jpg');

-- user_subscriptions
INSERT INTO "test".public.user_subscriptions (subscriber_id, user_id) VALUES (1, 2);
INSERT INTO "test".public.user_subscriptions (subscriber_id, user_id) VALUES (2, 1);
INSERT INTO "test".public.user_subscriptions (subscriber_id, user_id) VALUES (4, 2);
INSERT INTO "test".public.user_subscriptions (subscriber_id, user_id) VALUES (4, 1);

-- user_blocked
INSERT INTO "test".public.user_blocked (user_id, blocked_user_id) VALUES (2, 4);
INSERT INTO "test".public.user_blocked (user_id, blocked_user_id) VALUES (5, 2);
INSERT INTO "test".public.user_blocked (user_id, blocked_user_id) VALUES (6, 2);

-- lists
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (4, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 2, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (5, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 2', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (6, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 2, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (7, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (8, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (9, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (10, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 6, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, list_name, list_owner_id, wallpaper) VALUES (11, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 3, null);

-- pinned_lists
INSERT INTO "test".public.pinned_lists (pinned_date, list_id, pinned_user_id) VALUES ('2023-09-07 20:42:53.000000', 4, 2);
INSERT INTO "test".public.pinned_lists (pinned_date, list_id, pinned_user_id) VALUES ('2023-09-07 20:42:53.000000', 6, 2);

-- lists_followers
INSERT INTO "test".public.lists_followers (list_id, follower_id) VALUES (4, 1);
INSERT INTO "test".public.lists_followers (list_id, follower_id) VALUES (7, 2);

-- lists_members
INSERT INTO "test".public.lists_members (list_id, member_id) VALUES (4, 1);
INSERT INTO "test".public.lists_members (list_id, member_id) VALUES (5, 1);
INSERT INTO "test".public.lists_members (list_id, member_id) VALUES (7, 2);
INSERT INTO "test".public.lists_members (list_id, member_id) VALUES (9, 1);


# -- lists
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (54, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 2, null);
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (55, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (56, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 2, null);
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (57, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (58, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (59, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 1, null);
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (60, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 6, null);
# INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (61, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 3, null);
#
# -- pinned_lists
# INSERT INTO "test".public.pinned_lists (id, pinned_date, list_id, pinned_user_id) VALUES (1, '2023-09-07 20:42:53.000000', 54, 2);
# INSERT INTO "test".public.pinned_lists (id, pinned_date, list_id, pinned_user_id) VALUES (2, '2022-09-07 20:42:53.000000', 56, 2);
#
# -- lists_followers
# INSERT INTO "test".public.lists_followers (id, list_id, follower_id) VALUES (51, 54, 1);
# INSERT INTO "test".public.lists_followers (id, list_id, follower_id) VALUES (52, 57, 2);
#
# -- lists_members
# INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (51, 54, 1);
# INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (52, 55, 1);
# INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (53, 57, 2);
# INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (54, 59, 1);
