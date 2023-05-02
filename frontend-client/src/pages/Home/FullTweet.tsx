import { FC, ReactElement, useEffect } from "react";
import { useHomeStyles } from "./HomeStyles";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { Avatar, CircularProgress, Paper, Typography } from "@material-ui/core";
import {
  selectIsTweetLoading,
  selectTweetData,
} from "../../store/actions/tweet/selectors";
import {
  fetchTweetData,
  setTweetData,
} from "../../store/actions/tweet/actionCreators";
import classNames from "classnames";

export const FullTweet: FC = (): ReactElement | null => {
  const classes = useHomeStyles();
  const dispatch = useDispatch();
  const tweetData = useSelector(selectTweetData);
  const isLoading = useSelector(selectIsTweetLoading);
  const params: { id?: string } = useParams();
  const id = params.id;

  useEffect(() => {
    if (id) {
      dispatch(fetchTweetData(id));
    }

    return () => {
      dispatch(setTweetData(undefined));
    };
  }, [dispatch, id]);

  if (isLoading) {
    return (
      <div className={classes.tweetsCentred}>
        <CircularProgress />
      </div>
    );
  }

  if (tweetData) {
    return (
      <Paper className={classes.fullTweet}>
        <div className={classNames(classes.tweetsHeaderUser)}>
          <Avatar
            className={classes.tweetAvatar}
            alt={`User avatar ${tweetData.user.fullname}`}
            src={tweetData.user.avatarUrl}
          />
          <Typography>
            <b>{tweetData.user.fullname}</b>&nbsp;
            <div>
              <span className={classes.tweetUserName}>
                @{tweetData.user.username}
              </span>
              &nbsp;
              <span className={classes.tweetUserName}>·</span>&nbsp;
              <span className={classes.tweetUserName}>1 ч</span>
            </div>
          </Typography>
        </div>
        <Typography className={classes.fullTweetText} gutterBottom>
          {tweetData.text}
        </Typography>
      </Paper>
    );
  }

  return null;
};
