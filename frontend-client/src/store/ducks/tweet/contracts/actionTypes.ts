import {Action} from "redux";
import { LoadingStatus } from "../../../types";
import {TweetState} from "./state";

export enum TweetActionType {
    SET_TWEET_DATA = "tweet/SET_TWEET_DATA",
    FETCH_TWEET_DATA = "tweet/FETCH_TWEET_DATA",
    SET_LOADING_STATE = "tweet/SET_LOADING_STATE"
}

export interface SetTweetDataActionInterface extends Action<TweetActionType> {
    type: TweetActionType.SET_TWEET_DATA;
    payload: TweetState["data"];
}

export interface SetTweetDataLoadingStateInterface extends Action<TweetActionType> {
    type: TweetActionType.SET_LOADING_STATE;
    payload: LoadingStatus;
}

export interface FetchTweetDataActionInterface extends Action<TweetActionType> {
    type: TweetActionType.FETCH_TWEET_DATA;
    payload: string;
}

export type TweetActions =
    | SetTweetDataActionInterface
    | SetTweetDataLoadingStateInterface
    | FetchTweetDataActionInterface;
