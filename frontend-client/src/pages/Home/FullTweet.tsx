import { FC, ReactElement, useEffect } from "react";
import { useHomeStyles } from "./HomeStyles";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { CircularProgress } from "@material-ui/core";
import Tweet from "../../components/Tweet/Tweet";
import {
  selectIsTweetLoading,
  selectTweetData,
} from "../../store/actions/tweet/selectors";
import {
  fetchTweetData,
  setTweetData,
} from "../../store/actions/tweet/actionCreators";

const FullTweet: FC = (): ReactElement | null => {
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
    return <Tweet classes={classes} {...tweetData} />;
  }

  return null;
};

export default FullTweet;
