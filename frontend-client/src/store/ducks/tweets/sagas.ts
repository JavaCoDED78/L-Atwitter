import {call, put, takeLatest} from 'redux-saga/effects';

import {addTweet, likeTweet, setAddFormState, setTweets, setTweetsLoadingState} from "./actionCreators";
import {TweetsApi} from "../../../services/api/tweetsApi";
import {AddFormState, Tweet} from "./contracts/state";
import {
    FetchAddTweetActionInterface,
    FetchLikeTweetActionInterface,
    RemoveTweetActionInterface,
    TweetsActionType
} from "./contracts/actionTypes";
import {LoadingStatus} from '../../types';
import {setTweetData} from "../tweet/actionCreators";

export function* fetchTweetsRequest() {
    try {
        // ?????????????????
        const items: Tweet[] = yield call(TweetsApi.fetchTweets);
        yield put(setTweets(items));
    } catch (e) {
        yield put(setTweetsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchAddTweetRequest({payload}: FetchAddTweetActionInterface) {
    try {
        const item: Tweet[] = yield call(TweetsApi.addTweet, payload);
        yield put(addTweet(item));
    } catch (e) {
        yield put(setAddFormState(AddFormState.ERROR));
    }
}

export function* fetchRemoveTweetRequest({payload}: RemoveTweetActionInterface) {
    try {
        yield call(TweetsApi.removeTweet, payload);
    } catch (error) {
        alert('Ошибка при удалении твита');
    }
}

export function* fetchLikeTweetRequest({payload}: FetchLikeTweetActionInterface) {
    const item: Tweet = yield call(TweetsApi.likeTweet, payload);
    yield put(likeTweet(item));
    yield put(setTweetData(item));
}

export function* tweetsSaga() {
    yield takeLatest(TweetsActionType.FETCH_TWEETS, fetchTweetsRequest);
    yield takeLatest(TweetsActionType.FETCH_ADD_TWEET, fetchAddTweetRequest);
    yield takeLatest(TweetsActionType.REMOVE_TWEET, fetchRemoveTweetRequest);
    yield takeLatest(TweetsActionType.FETCH_LIKE_TWEET, fetchLikeTweetRequest);
}
