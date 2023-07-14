import {AddQuoteTweet, AddTweet, ReplyType, Tweet, TweetsState, Vote} from "./contracts/state";
import {
    DeleteTweetActionInterface,
    FetchAddPollActionInterface,
    FetchAddQuoteTweetActionInterface,
    FetchAddScheduledTweetActionInterface,
    FetchAddTweetActionInterface,
    FetchBookmarksActionInterface,
    FetchChangeReplyTypeActionInterface,
    FetchDeleteScheduledTweetsActionInterface,
    FetchDeleteTweetActionInterface,
    FetchLikedTweetsActionInterface,
    FetchLikeTweetActionInterface,
    FetchMediaTweetsActionInterface,
    FetchRetweetActionInterface,
    FetchTweetsActionInterface,
    FetchTweetsByTagActionInterface,
    FetchTweetsByTextActionInterface,
    FetchTweetsWithVideoActionInterface,
    FetchUpdateScheduledTweetActionInterface,
    FetchVoteActionInterface,
    RemoveTweetFromBookmarksActionInterface,
    ResetTweetsActionInterface,
    SetPageableTweetsActionInterface,
    SetScheduledTweetsActionInterface,
    SetTweetActionInterface,
    SetTweetsActionInterface,
    SetTweetsLoadingStateInterface,
    SetUpdatedTweetActionInterface,
    TweetsActionType,
} from "./contracts/actionTypes";
import {LoadingStatus} from "../../types";

export const setTweets = (payload: TweetsState["items"]): SetTweetsActionInterface => ({
    type: TweetsActionType.SET_TWEETS,
    payload
});

export const setScheduledTweets = (payload: TweetsState["items"]): SetScheduledTweetsActionInterface => ({
    type: TweetsActionType.SET_SCHEDULED_TWEETS,
    payload
});

export const setPageableTweets = (payload: { items: TweetsState["items"], pagesCount: TweetsState["pagesCount"] }): SetPageableTweetsActionInterface => ({
    type: TweetsActionType.SET_PAGEABLE_TWEETS,
    payload
});

export const setTweet = (payload: Tweet): SetTweetActionInterface => ({
    type: TweetsActionType.SET_TWEET,
    payload
});

export const resetTweets = (): ResetTweetsActionInterface => ({
    type: TweetsActionType.RESET_TWEETS,
});

export const fetchAddTweet = (payload: AddTweet): FetchAddTweetActionInterface => ({
    type: TweetsActionType.FETCH_ADD_TWEET,
    payload
});

export const fetchAddPoll = (payload: AddTweet): FetchAddPollActionInterface => ({
    type: TweetsActionType.FETCH_ADD_POLL,
    payload
});

export const fetchAddScheduledTweet = (payload: AddTweet): FetchAddScheduledTweetActionInterface => ({
    type: TweetsActionType.FETCH_ADD_SCHEDULED_TWEET,
    payload
});

export const fetchUpdateScheduledTweet = (payload: AddTweet): FetchUpdateScheduledTweetActionInterface => ({
    type: TweetsActionType.FETCH_UPDATE_SCHEDULED_TWEET,
    payload
});

export const fetchAddQuoteTweet = (payload: AddQuoteTweet): FetchAddQuoteTweetActionInterface => ({
    type: TweetsActionType.FETCH_ADD_QUOTE_TWEET,
    payload
});

export const fetchVote = (payload: Vote): FetchVoteActionInterface => ({
    type: TweetsActionType.FETCH_VOTE,
    payload
});

export const fetchChangeReplyType = (payload: { tweetId: string; replyType: ReplyType; }): FetchChangeReplyTypeActionInterface => ({
    type: TweetsActionType.FETCH_CHANGE_REPLY_TYPE,
    payload
});

export const setUpdatedTweet = (payload: Tweet): SetUpdatedTweetActionInterface => ({
    type: TweetsActionType.SET_UPDATED_TWEET,
    payload
});

export const fetchDeleteTweet = (payload: string): FetchDeleteTweetActionInterface => ({
    type: TweetsActionType.FETCH_DELETE_TWEET,
    payload
});

export const fetchDeleteScheduledTweets = (payload: { tweetsIds: number[] }): FetchDeleteScheduledTweetsActionInterface => ({
    type: TweetsActionType.FETCH_DELETE_SCHEDULED_TWEETS,
    payload
});

export const deleteTweet = (payload: Tweet): DeleteTweetActionInterface => ({
    type: TweetsActionType.DELETE_TWEET,
    payload
});

export const fetchTweetsByTag = (payload: string): FetchTweetsByTagActionInterface => ({
    type: TweetsActionType.FETCH_TWEETS_BY_TAG,
    payload
});

export const fetchTweetsByText = (payload: string): FetchTweetsByTextActionInterface => ({
    type: TweetsActionType.FETCH_TWEETS_BY_TEXT,
    payload
});

export const fetchLikedTweets = (payload: string): FetchLikedTweetsActionInterface => ({
    type: TweetsActionType.FETCH_LIKED_TWEETS,
    payload
});

export const setTweetsLoadingState = (payload: LoadingStatus): SetTweetsLoadingStateInterface => ({
    type: TweetsActionType.SET_LOADING_STATE,
    payload
});

export const fetchLikeTweet = (payload: string): FetchLikeTweetActionInterface => ({
    type: TweetsActionType.FETCH_LIKE_TWEET,
    payload,
});

export const fetchRetweet = (payload: string): FetchRetweetActionInterface => ({
    type: TweetsActionType.FETCH_RETWEET,
    payload,
});

export const fetchTweets = (payload: number): FetchTweetsActionInterface => ({
    type: TweetsActionType.FETCH_TWEETS,
    payload,
});

export const fetchMediaTweets = (payload: number): FetchMediaTweetsActionInterface => ({
    type: TweetsActionType.FETCH_MEDIA_TWEETS,
    payload
});

export const fetchTweetsWithVideo = (payload: number): FetchTweetsWithVideoActionInterface => ({
    type: TweetsActionType.FETCH_TWEETS_WITH_VIDEO,
    payload
});

export const fetchUserBookmarks = (payload: number): FetchBookmarksActionInterface => ({
    type: TweetsActionType.FETCH_BOOKMARKS,
    payload
});

export const removeTweetFromBookmarks = (payload: string): RemoveTweetFromBookmarksActionInterface => ({
    type: TweetsActionType.REMOVE_TWEET_FROM_BOOKMARKS,
    payload,
});
