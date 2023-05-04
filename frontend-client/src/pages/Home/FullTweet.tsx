import { FC, ReactElement, useEffect } from "react";
import { useHomeStyles } from "./HomeStyles";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import {
  Avatar,
  CircularProgress,
  Divider,
  Paper,
  Typography,
} from "@material-ui/core";
import {
  selectIsTweetLoading,
  selectTweetData,
} from "../../store/actions/tweet/selectors";
import {
  fetchTweetData,
  setTweetData,
} from "../../store/actions/tweet/actionCreators";
import classNames from "classnames";
import format from "date-fns/format";
import ruLang from "date-fns/locale/ru";
import IconButton from "@material-ui/core/IconButton";
import {
  FavoriteBorderOutlined,
  ModeCommentOutlined,
  RepeatOutlined,
  ReplyOutlined,
} from "@material-ui/icons";
import Tweet from "../../components/Tweet/Tweet";

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
      <>
        <Paper className={classes.fullTweet}>
          <div className={classNames(classes.tweetsHeaderUser)}>
            <Avatar
              className={classes.tweetAvatar}
              // alt={`Аватарка пользователя ${tweetData.user.fullName}`}
              // src={tweetData.user.avatarUrl}
              alt={`Аватарка пользователя`}
              src={
                "https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg"
              }
            />
            <Typography>
              <b>{tweetData.user.fullName}</b>&nbsp;
              <div>
                <span className={classes.tweetUserName}>
                  @{tweetData.user.username}
                </span>
                &nbsp;
              </div>
            </Typography>
          </div>
          <Typography className={classes.fullTweetText} gutterBottom>
            {tweetData.text}
          </Typography>
          <Typography>
            <span className={classes.tweetUserName}>
              {format(new Date(tweetData.dateTime), "H:mm", { locale: ruLang })}{" "}
              ·{" "}
            </span>
            <span className={classes.tweetUserName}>
              {format(new Date(tweetData.dateTime), "dd MMM. yyyy г.")}
            </span>
          </Typography>
          <div
            className={classNames(classes.tweetFooter, classes.fullTweetFooter)}
          >
            <IconButton>
              <ModeCommentOutlined style={{ fontSize: 25 }} />
            </IconButton>
            <IconButton>
              <RepeatOutlined style={{ fontSize: 25 }} />
            </IconButton>
            <IconButton>
              <FavoriteBorderOutlined style={{ fontSize: 25 }} />
            </IconButton>
            <IconButton>
              <ReplyOutlined style={{ fontSize: 25 }} />
            </IconButton>
          </div>
        </Paper>
        <Divider />
        <Tweet
          id="1"
          text="Any more to move? You might need to adjust your stretching routines!"
          dateTime={new Date().toString()}
          user={{
            fullName: "Arlene Andrews",
            username: "ArleneAndrews_1",
            avatarUrl:
              "https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg",
          }}
          classes={classes}
        />
        <Tweet
          id="1"
          text="Any more to move? You might need to adjust your stretching routines!"
          dateTime={new Date().toString()}
          user={{
            fullName: "Arlene Andrews",
            username: "ArleneAndrews_1",
            avatarUrl:
              "https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg",
          }}
          classes={classes}
        />
        <Tweet
          id="1"
          text="Any more to move? You might need to adjust your stretching routines!"
          dateTime={new Date().toString()}
          user={{
            fullName: "Arlene Andrews",
            username: "ArleneAndrews_1",
            avatarUrl:
              "https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg",
          }}
          classes={classes}
        />
      </>
    );
  }

  return null;
};
