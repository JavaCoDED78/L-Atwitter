import {Tweet, UserTweetsState} from "./contracts/state";
import {LoadingStatus} from "../../types";
import {
    FetchUserLikedTweetsActionInterface,
    FetchUserTweetsActionInterface,
    SetUserLikedTweetActionInterface,
    SetUserRetweetActionInterface,
    SetUserTweetsActionInterface,
    SetUserTweetsLoadingStatusInterface,
    UserTweetsActionType
} from "./contracts/actionTypes";

export const setUserTweets = (payload: UserTweetsState["items"]): SetUserTweetsActionInterface => ({
    type: UserTweetsActionType.SET_TWEETS,
    payload
});

export const fetchUserTweets = (payload: string): FetchUserTweetsActionInterface => ({
    type: UserTweetsActionType.FETCH_TWEETS,
    payload
});

export const setUserLikedTweet = (payload: Tweet): SetUserLikedTweetActionInterface => ({
    type: UserTweetsActionType.SET_LIKED_TWEET,
    payload,
});

export const fetchUserLikedTweets = (payload: string): FetchUserLikedTweetsActionInterface => ({
    type: UserTweetsActionType.FETCH_LIKED_TWEETS,
    payload
});

export const setUserRetweet = (payload: Tweet): SetUserRetweetActionInterface => ({
    type: UserTweetsActionType.SET_RETWEET,
    payload,
});

export const setUserTweetsLoadingStatus = (payload: LoadingStatus): SetUserTweetsLoadingStatusInterface => ({
    type: UserTweetsActionType.SET_LOADING_STATUS,
    payload
});
