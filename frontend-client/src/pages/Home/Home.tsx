import React, { FC, ReactElement, useEffect } from "react";
import {
  Avatar,
  Button,
  Container,
  Divider,
  InputAdornment,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Paper,
  Typography,
  Grid,
  CircularProgress,
} from "@material-ui/core";

import Tweet from "../../components/Tweet/Tweet";
import SideMenu from "../../components/SideMenu/SideMenu";
import { useHomeStyles } from "./HomeStyles";
import SearchIcon from "@material-ui/icons/Search";
import { PersonAddOutlined } from "@material-ui/icons";
import { SearchTextField } from "../../components/Tweet/SearchTextField";
import { AddTweetForm } from "../../components/TweetForm/AddTweetForm";
import Tags from "../../components/Tags/Tags";
import { useDispatch, useSelector } from "react-redux";
import {
  selectIsTweetsLoading,
  selectTweetsItems,
} from "../../store/actions/tweets/selectors";
import { fetchTweets } from "../../store/actions/tweets/actionCreators";
import { fetchTags } from "../../store/actions/tags/actionCreators";
import { Route } from "react-router-dom";
import BackButton from "../../components/BackButton/BackButton";
import FullTweet from "./FullTweet";

const Home: FC = (): ReactElement => {
  const classes = useHomeStyles();
  const dispatch = useDispatch();
  const tweets = useSelector(selectTweetsItems);
  const isLoading = useSelector(selectIsTweetsLoading);

  useEffect(() => {
    dispatch(fetchTweets());
    dispatch(fetchTags());
  }, [dispatch]);

  return (
    <Container className={classes.wrapper} maxWidth="lg">
      <Grid container spacing={3}>
        <Grid item sm={1} md={3}>
          <SideMenu classes={classes} />
        </Grid>
        <Grid item sm={8} md={6}>
          <Paper className={classes.tweetsWrapper} variant="outlined">
            <Paper className={classes.tweetsHeader} variant="outlined">
              <Route path="/home/:any">
                <BackButton />
              </Route>
              <Route path={["/home", "/home/search"]} exact>
                <Typography variant="h6">Home</Typography>
              </Route>
              <Route path="/home/tweet">
                <Typography variant="h6">Tweet</Typography>
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
            <Route exact path={"/home"}>
              {isLoading ? (
                <div className={classes.tweetsCentred}>
                  <CircularProgress />
                </div>
              ) : (
                tweets.map((tweet) => (
                  <Tweet key={tweet._id} classes={classes} {...tweet} />
                ))
              )}
            </Route>
            <Route path="/home/tweet/:id" component={FullTweet} exact />
          </Paper>
        </Grid>
        <Grid item sm={3} md={3}>
          <div className={classes.rightSide}>
            <SearchTextField
              variant="outlined"
              placeholder="Search Twitter"
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchIcon />
                  </InputAdornment>
                ),
              }}
              fullWidth
            />
            <Tags classes={classes} />
            <Paper className={classes.rightSideBlock}>
              <Paper
                className={classes.rightSideBlockHeader}
                variant="outlined"
              >
                <b>Who read</b>
              </Paper>
              <List>
                <ListItem className={classes.rightSideBlockItem}>
                  <ListItemAvatar>
                    <Avatar
                      alt="Current Current"
                      src="https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg"
                    />
                  </ListItemAvatar>
                  <ListItemText
                    primary="Ba la bol"
                    secondary={
                      <Typography
                        component="span"
                        variant="body2"
                        color="textSecondary"
                      >
                        @Balabol
                      </Typography>
                    }
                  />
                  <Button color="primary">
                    <PersonAddOutlined />
                  </Button>
                </ListItem>
                <Divider component="li" />
              </List>
            </Paper>
          </div>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Home;
