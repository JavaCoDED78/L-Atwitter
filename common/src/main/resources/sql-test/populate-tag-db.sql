-- tags
INSERT INTO tags (id, tag_name, tweets_quantity) VALUES (52, '#JetBrains', 2);
INSERT INTO tags (id, tag_name, tweets_quantity) VALUES (53, '#test', 1);

-- tweet_tags
INSERT INTO tweet_tags (id, tag_id, tweet_id) VALUES (51, 52, 16);
INSERT INTO tweet_tags (id, tag_id, tweet_id) VALUES (52, 53, 40);
