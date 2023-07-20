import {call, put, takeLatest} from 'redux-saga/effects';
import {AxiosResponse} from "axios";

import {setUserTweets, setUserTweetsLoadingStatus} from "./actionCreators";
import {
    FetchUserLikedTweetsActionInterface,
    FetchUserMediaTweetsActionInterface,
    FetchUserRetweetsAndRepliesActionInterface,
    FetchUserTweetsActionInterface,
    UserTweetsActionType
} from "./contracts/actionTypes";
import {LoadingStatus} from '../../types';
import {UserApi} from "../../../services/api/userApi";
import {TweetResponse} from "../../types/tweet";

export function* fetchUserTweetsRequest({payload}: FetchUserTweetsActionInterface) {
    try {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.LOADING));
        const response: AxiosResponse<TweetResponse[]> = yield call(UserApi.getUserTweets, payload);
        yield put(setUserTweets({items: response.data, pagesCount: parseInt(response.headers["page-total-count"])}));
    } catch (e) {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.ERROR));
    }
}

export function* fetchUserLikedTweetsRequest({payload}: FetchUserLikedTweetsActionInterface) {
    try {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.LOADING));
        const response: AxiosResponse<TweetResponse[]> = yield call(UserApi.getUserLikedTweets, payload);
        yield put(setUserTweets({items: response.data, pagesCount: parseInt(response.headers["page-total-count"])}));
    } catch (e) {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.ERROR));
    }
}

export function* fetchUserMediaTweetsRequest({payload}: FetchUserMediaTweetsActionInterface) {
    try {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.LOADING));
        const response: AxiosResponse<TweetResponse[]> = yield call(UserApi.getUserMediaTweets, payload);
        yield put(setUserTweets({items: response.data, pagesCount: parseInt(response.headers["page-total-count"])}));
    } catch (e) {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.ERROR));
    }
}

export function* fetchUserRetweetsAndRepliesRequest({payload}: FetchUserRetweetsAndRepliesActionInterface) {
    try {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.LOADING));
        const response: AxiosResponse<TweetResponse[]> = yield call(UserApi.getUserRetweetsAndReplies, payload);
        yield put(setUserTweets({items: response.data, pagesCount: parseInt(response.headers["page-total-count"])}));
    } catch (e) {
        yield put(setUserTweetsLoadingStatus(LoadingStatus.ERROR));
    }
}

export function* userTweetsSaga() {
    yield takeLatest(UserTweetsActionType.FETCH_TWEETS, fetchUserTweetsRequest);
    yield takeLatest(UserTweetsActionType.FETCH_LIKED_TWEETS, fetchUserLikedTweetsRequest);
    yield takeLatest(UserTweetsActionType.FETCH_MEDIA_TWEETS, fetchUserMediaTweetsRequest);
    yield takeLatest(UserTweetsActionType.FETCH_RETWEETS_AND_REPLIES, fetchUserRetweetsAndRepliesRequest);
}
