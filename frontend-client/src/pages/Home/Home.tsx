import React, { FC, ReactElement, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Route, useLocation } from "react-router-dom";
import {
  CircularProgress,
  IconButton,
  Paper,
  Typography,
} from "@material-ui/core";

import TweetComponent from "../../components/TweetComponent/TweetComponent";
import { useHomeStyles } from "./HomeStyles";
import { AddTweetForm } from "../../components/AddTweetForm/AddTweetForm";
import {
  fetchTweets,
  setTweetsLoadingState,
} from "../../store/ducks/tweets/actionCreators";
import {
  selectIsTweetsLoading,
  selectTweetsItems,
} from "../../store/ducks/tweets/selectors";
import { BackButton } from "../../components/BackButton/BackButton";
import { FullTweet } from "../FullTweet/FullTweet";
import { fetchUserData } from "../../store/ducks/user/actionCreators";
import { fetchRelevantUsers } from "../../store/ducks/users/actionCreators";
import { fetchTags } from "../../store/ducks/tags/actionCreators";
import Connect from "../Connect/Connect";
import Trends from "../Trends/Trends";
import { TopTweets } from "../../icons";
import { selectUserData } from "../../store/ducks/user/selectors";
import Welcome from "../../components/Welcome/Welcome";
import { LoadingStatus } from "../../store/types";

const Home: FC = (): ReactElement => {
  const classes = useHomeStyles();
  const dispatch = useDispatch();
  const location = useLocation<{ background: Location }>();
  const myProfile = useSelector(selectUserData);
  const tweets = useSelector(selectTweetsItems);
  const isLoading = useSelector(selectIsTweetsLoading);

  useEffect(() => {
    dispatch(setTweetsLoadingState(LoadingStatus.NEVER));
    dispatch(fetchUserData());
    dispatch(fetchTags());

    if (location.pathname !== "/search") {
      dispatch(fetchTweets());
    }
    if (location.pathname !== "/home/connect") {
      dispatch(fetchRelevantUsers());
    }
    document.body.style.overflow = "unset";
    window.scrollTo(0, 0);
  }, []);

  return (
    <Paper className={classes.container} variant="outlined">
      <Paper className={classes.header} variant="outlined">
        <Route path="/home" exact>
          <Typography variant="h6">Home</Typography>
          <div className={classes.headerIcon}>
            <IconButton color="primary">
              <span>{TopTweets}</span>
            </IconButton>
          </div>
        </Route>
        <Route path="/home/tweet">
          <BackButton />
          <Typography variant="h6">Tweet</Typography>
        </Route>
        <Route path="/home/connect">
          <BackButton />
          <Typography variant="h6">Connect</Typography>
        </Route>
        <Route path="/home/trends">
          <BackButton />
          <Typography variant="h6">Trends</Typography>
        </Route>
      </Paper>

      <Route path="/home" exact>
        <Paper variant="outlined">
          <div className={classes.addForm}>
            <AddTweetForm title={"What's happening?"} buttonName={"Tweet"} />
          </div>
          <div className={classes.divider} />
        </Paper>
      </Route>

      <Route path="/home/connect" exact>
        <Connect />
      </Route>

      <Route path="/home/trends" exact>
        <Trends />
      </Route>

      <Route path="/home" exact>
        {isLoading ? (
          <div className={classes.loading}>
            <CircularProgress />
          </div>
        ) : !myProfile?.profileStarted ? (
          <Welcome />
        ) : (
          tweets.map((tweet) => (
            <TweetComponent key={tweet.id} images={tweet.images} {...tweet} />
          ))
        )}
      </Route>

      <Route path="/home/tweet/:id" exact>
        <FullTweet />
      </Route>
    </Paper>
  );
};

export default Home;
