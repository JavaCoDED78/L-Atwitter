import React, { FC, ReactElement } from "react";
import classNames from "classnames";
import { Avatar, IconButton, Paper, Typography } from "@material-ui/core";
import CommentIcon from "@material-ui/icons/ModeCommentOutlined";
import RepostIcon from "@material-ui/icons/RepeatOutlined";
import LikeIcon from "@material-ui/icons/FavoriteBorderOutlined";
import ReplyIcon from "@material-ui/icons/ReplyOutlined";
import { useHomeStyles } from "../../pages/Home/HomeStyles";
import { Link } from "react-router-dom";

interface TweetProps {
  _id: string;
  classes: ReturnType<typeof useHomeStyles>;
  text: string;
  user: {
    fullname: string;
    username: string;
    avatarUrl: string;
  };
}

const Tweet: FC<TweetProps> = ({
  _id,
  classes,
  text,
  user,
}: TweetProps): ReactElement => {
  return (
    <Link className={classes.tweetWrapper} to={`/home/tweet/${_id}`}>
      <Paper
        className={classNames(classes.tweet, classes.tweetsHeader)}
        variant="outlined"
      >
        <Avatar
          className={classes.tweetAvatar}
          alt={`Avatar ${user.fullname}`}
          src={user.avatarUrl}
        />
        <div>
          <Typography>
            <b>{user.fullname} </b>
            <span className={classes.tweetUserName}>@{user.username} </span>
            <span className={classes.tweetUserName}>&sdot; </span>
            <span className={classes.tweetUserName}>1 h</span>
          </Typography>
          <Typography variant="body1" gutterBottom>
            {text}
          </Typography>
          <div className={classes.tweetFooter}>
            <div>
              <IconButton>
                <CommentIcon style={{ fontSize: 20 }} />
              </IconButton>
              <span>1</span>
            </div>
            <div>
              <IconButton>
                <RepostIcon style={{ fontSize: 20 }} />
              </IconButton>
            </div>
            <div>
              <IconButton>
                <LikeIcon style={{ fontSize: 20 }} />
              </IconButton>
            </div>
            <div>
              <IconButton>
                <ReplyIcon style={{ fontSize: 20 }} />
              </IconButton>
            </div>
          </div>
        </div>
      </Paper>
    </Link>
  );
};

export default Tweet;
