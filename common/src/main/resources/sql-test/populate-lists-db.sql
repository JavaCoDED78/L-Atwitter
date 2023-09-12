-- lists
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (54, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 2, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (55, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (56, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 2, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (57, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (58, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', true, 'test list name 1', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (59, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 1, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (60, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 6, null);
INSERT INTO "test".public.lists (id, alt_wallpaper, description, private, name, list_owner_id, wallpaper) VALUES (61, 'https://pbs.twimg.com/media/EXZ2rMvVAAAAfrN?format=png&name=small', 'test list description', false, 'test list name 1', 3, null);

-- pinned_lists
INSERT INTO "test".public.pinned_lists (id, pinned_date, list_id, pinned_user_id) VALUES (1, '2023-09-07 20:42:53.000000', 54, 2);
INSERT INTO "test".public.pinned_lists (id, pinned_date, list_id, pinned_user_id) VALUES (2, '2022-09-07 20:42:53.000000', 56, 2);

-- lists_followers
INSERT INTO "test".public.lists_followers (id, list_id, follower_id) VALUES (51, 54, 1);
INSERT INTO "test".public.lists_followers (id, list_id, follower_id) VALUES (52, 57, 2);

-- lists_members
INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (51, 54, 1);
INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (52, 55, 1);
INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (53, 57, 2);
INSERT INTO "test".public.lists_members (id, list_id, member_id) VALUES (54, 59, 1);
