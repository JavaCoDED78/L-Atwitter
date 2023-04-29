import { LoadingState, TweetState } from "./contracts/state";
import {
  FetchTweetDataActionInterface,
  SetTweetDataActionInterface,
  SetTweetDataLoadingStateInterface,
  TweetActionType,
} from "./contracts/actionTypes";

export const setTweetData = (
  payload: TweetState["data"]
): SetTweetDataActionInterface => ({
  type: TweetActionType.SET_TWEET_DATA,
  payload,
});

export const setTweetLoadingState = (
  payload: LoadingState
): SetTweetDataLoadingStateInterface => ({
  type: TweetActionType.SET_LOADING_STATE,
  payload,
});

export const fetchTweetData = (
  payload: string
): FetchTweetDataActionInterface => ({
  type: TweetActionType.FETCH_TWEET_DATA,
  payload,
});

export type TweetActions =
  | SetTweetDataActionInterface
  | SetTweetDataLoadingStateInterface
  | FetchTweetDataActionInterface;
