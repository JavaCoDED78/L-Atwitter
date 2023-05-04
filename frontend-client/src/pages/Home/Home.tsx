import React, { FC, ReactElement, useEffect } from "react";
import { Paper, Typography, CircularProgress } from "@material-ui/core";

import Tweet from "../../components/Tweet/Tweet";
import { useHomeStyles } from "./HomeStyles";
import { AddTweetForm } from "../../components/TweetForm/AddTweetForm";
import { useDispatch, useSelector } from "react-redux";
import {
  selectIsTweetsLoading,
  selectTweetsItems,
} from "../../store/actions/tweets/selectors";
import { Route } from "react-router-dom";
import BackButton from "../../components/BackButton/BackButton";
import { FullTweet } from "./FullTweet";

const Home: FC = (): ReactElement => {
  const dispatch = useDispatch();
  const classes = useHomeStyles();
  const tweets = useSelector(selectTweetsItems);
  const isLoading = useSelector(selectIsTweetsLoading);

  // useEffect(() => {
  //   dispatch(fetchTweets());
  //   dispatch(fetchTags());
  // }, [dispatch]);

  return (
    <Paper className={classes.tweetsWrapper} variant="outlined">
      <Paper className={classes.tweetsHeader} variant="outlined">
        <Route path="/home/:any">
          <BackButton />
        </Route>

        <Route path={["/home", "/home/search"]} exact>
          <Typography variant="h6">Твиты</Typography>
        </Route>

        <Route path="/home/tweet">
          <Typography variant="h6">Твитнуть</Typography>
        </Route>
      </Paper>

      <Route path={["/home", "/home/search"]} exact>
        <Paper>
          <div className={classes.addForm}>
            <AddTweetForm classes={classes} />
          </div>
          <div className={classes.addFormBottomLine} />
        </Paper>
      </Route>

      <Route path="/home" exact>
        {isLoading ? (
          <div className={classes.tweetsCentred}>
            <CircularProgress />
          </div>
        ) : (
          tweets.map((tweet) => (
            <Tweet key={tweet.id} classes={classes} {...tweet} />
          ))
        )}
      </Route>

      <Route path="/home/tweet/:id" component={FullTweet} exact />
    </Paper>
  );
};

export default Home;
