import {call, put, takeLatest} from 'redux-saga/effects';

import {fetchAddScheduledTweet, setPageableTweets, setTweets, setTweetsLoadingState,} from "./actionCreators";
import {TweetApi} from "../../../services/api/tweetApi";
import {Tweet} from "./contracts/state";
import {
    FetchAddPollActionInterface,
    FetchAddQuoteTweetActionInterface, FetchAddScheduledTweetActionInterface,
    FetchAddTweetActionInterface,
    FetchBookmarksActionInterface,
    FetchChangeReplyTypeActionInterface, FetchDeleteScheduledTweetsActionInterface,
    FetchDeleteTweetActionInterface,
    FetchLikedTweetsActionInterface,
    FetchLikeTweetActionInterface,
    FetchMediaTweetsActionInterface,
    FetchRetweetActionInterface,
    FetchTweetsActionInterface,
    FetchTweetsByTagActionInterface,
    FetchTweetsByTextActionInterface,
    FetchTweetsWithVideoActionInterface, FetchUpdateScheduledTweetActionInterface,
    FetchVoteActionInterface,
    TweetsActionType
} from "./contracts/actionTypes";
import {LoadingStatus} from '../../types';
import {TagApi} from "../../../services/api/tagApi";
import {UserApi} from "../../../services/api/userApi";
import {AxiosResponse} from "axios";

export function* fetchTweetsRequest({payload}: FetchTweetsActionInterface) {
    try {
        yield put(setTweetsLoadingState(LoadingStatus.LOADING));
        const response: AxiosResponse<Tweet[]> = yield call(TweetApi.fetchTweets, payload);
        yield put(setPageableTweets({
            items: response.data,
            pagesCount: parseInt(response.headers["page-total-count"])
        }));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchMediaTweetsRequest({payload}: FetchMediaTweetsActionInterface) {
    try {
        yield put(setTweetsLoadingState(LoadingStatus.LOADING));
        const response: AxiosResponse<Tweet[]> = yield call(TweetApi.fetchMediaTweets, payload);
        yield put(setPageableTweets({
            items: response.data,
            pagesCount: parseInt(response.headers["page-total-count"])
        }));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchTweetsWithVideoRequest({payload}: FetchTweetsWithVideoActionInterface) {
    try {
        yield put(setTweetsLoadingState(LoadingStatus.LOADING));
        const response: AxiosResponse<Tweet[]> = yield call(TweetApi.fetchTweetsWithVideo, payload);
        yield put(setPageableTweets({
            items: response.data,
            pagesCount: parseInt(response.headers["page-total-count"])
        }));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchTweetsByTagRequest({payload}: FetchTweetsByTagActionInterface) {
    try {
        yield put(setTweetsLoadingState(LoadingStatus.LOADING));
        const item: Tweet[] = yield call(TagApi.fetchTweetsByTag, payload);
        yield put(setTweets(item));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchTweetsByTextRequest({payload}: FetchTweetsByTextActionInterface) {
    try {
        yield put(setTweetsLoadingState(LoadingStatus.LOADING));
        const item: Tweet[] = yield call(TweetApi.searchTweets, payload);
        yield put(setTweets(item));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchLikedTweetsRequest({payload}: FetchLikedTweetsActionInterface) {
    try {
        yield put(setTweetsLoadingState(LoadingStatus.LOADING));
        const item: Tweet[] = yield call(UserApi.getUserLikedTweets, payload);
        yield put(setTweets(item));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchAddTweetRequest({payload}: FetchAddTweetActionInterface) {
    try {
        yield call(TweetApi.createTweet, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchAddPollRequest({payload}: FetchAddPollActionInterface) {
    try {
        yield call(TweetApi.createPoll, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchAddScheduledTweetRequest({payload}: FetchAddScheduledTweetActionInterface) {
    try {
        yield call(TweetApi.createScheduledTweet, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchUpdateScheduledTweetRequest({payload}: FetchUpdateScheduledTweetActionInterface) {
    try {
        yield call(TweetApi.updateScheduledTweet, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchAddQuoteTweet({payload}: FetchAddQuoteTweetActionInterface) {
    try {
        yield call(TweetApi.quoteTweet, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchVoteRequest({payload}: FetchVoteActionInterface) {
    try {
        yield call(TweetApi.voteInPoll, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchChangeReplyTypeRequest({payload}: FetchChangeReplyTypeActionInterface) {
    try {
        yield call(TweetApi.changeTweetReplyType, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchDeleteTweetRequest({payload}: FetchDeleteTweetActionInterface) {
    try {
        yield call(TweetApi.deleteTweet, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchDeleteScheduledTweetsTweetRequest({payload}: FetchDeleteScheduledTweetsActionInterface) {
    try {
        yield call(TweetApi.deleteScheduledTweets, payload);
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchLikeTweetRequest({payload}: FetchLikeTweetActionInterface) {
    yield call(TweetApi.likeTweet, payload);
}

export function* fetchRetweetRequest({payload}: FetchRetweetActionInterface) {
    yield call(TweetApi.retweet, payload);
}

export function* fetchUserBookmarksRequest({payload}: FetchBookmarksActionInterface) {
    try {
        yield put(setTweetsLoadingState(LoadingStatus.LOADING));
        const response: AxiosResponse<Tweet[]> = yield call(UserApi.getUserBookmarks, payload);
        yield put(setPageableTweets({
            items: response.data,
            pagesCount: parseInt(response.headers["page-total-count"])
        }));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* tweetsSaga() {
    yield takeLatest(TweetsActionType.FETCH_TWEETS, fetchTweetsRequest);
    yield takeLatest(TweetsActionType.FETCH_MEDIA_TWEETS, fetchMediaTweetsRequest);
    yield takeLatest(TweetsActionType.FETCH_TWEETS_WITH_VIDEO, fetchTweetsWithVideoRequest);
    yield takeLatest(TweetsActionType.FETCH_ADD_TWEET, fetchAddTweetRequest);
    yield takeLatest(TweetsActionType.FETCH_ADD_POLL, fetchAddPollRequest);
    yield takeLatest(TweetsActionType.FETCH_ADD_SCHEDULED_TWEET, fetchAddScheduledTweetRequest);
    yield takeLatest(TweetsActionType.FETCH_UPDATE_SCHEDULED_TWEET, fetchUpdateScheduledTweetRequest);
    yield takeLatest(TweetsActionType.FETCH_ADD_QUOTE_TWEET, fetchAddQuoteTweet);
    yield takeLatest(TweetsActionType.FETCH_VOTE, fetchVoteRequest);
    yield takeLatest(TweetsActionType.FETCH_CHANGE_REPLY_TYPE, fetchChangeReplyTypeRequest);
    yield takeLatest(TweetsActionType.FETCH_DELETE_TWEET, fetchDeleteTweetRequest);
    yield takeLatest(TweetsActionType.FETCH_DELETE_SCHEDULED_TWEETS, fetchDeleteScheduledTweetsTweetRequest);
    yield takeLatest(TweetsActionType.FETCH_LIKE_TWEET, fetchLikeTweetRequest);
    yield takeLatest(TweetsActionType.FETCH_RETWEET, fetchRetweetRequest);
    yield takeLatest(TweetsActionType.FETCH_TWEETS_BY_TAG, fetchTweetsByTagRequest);
    yield takeLatest(TweetsActionType.FETCH_TWEETS_BY_TEXT, fetchTweetsByTextRequest);
    yield takeLatest(TweetsActionType.FETCH_LIKED_TWEETS, fetchLikedTweetsRequest);
    yield takeLatest(TweetsActionType.FETCH_BOOKMARKS, fetchUserBookmarksRequest);
}
