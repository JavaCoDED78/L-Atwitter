import {call, put, takeEvery} from 'redux-saga/effects';

import {CreateListActionInterface, ListsActionType} from "./contracts/actionTypes";
import {setList, setLists, setListsLoadingState, setUserLists} from './actionCreators';
import {LoadingStatus} from '../../types';
import {ListsApi} from "../../../services/api/listsApi";
import {Lists} from "./contracts/state";

export function* fetchListsRequest() {
    try {
        yield put(setListsLoadingState(LoadingStatus.LOADING));
        const data: Lists[] = yield call(ListsApi.getAllTweetLists);
        yield put(setLists(data));
    } catch (error) {
        yield put(setListsLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchUserListsRequest() {
    try {
        yield put(setListsLoadingState(LoadingStatus.LOADING));
        const data: Lists[] = yield call(ListsApi.getUserTweetLists);
        yield put(setUserLists(data));
    } catch (error) {
        yield put(setListsLoadingState(LoadingStatus.ERROR));
    }
}

export function* createListRequest({payload}: CreateListActionInterface) {
    try {
        yield put(setListsLoadingState(LoadingStatus.LOADING));
        const data: Lists = yield call(ListsApi.createTweetList, payload);
        yield put(setList(data));
    } catch (error) {
        yield put(setListsLoadingState(LoadingStatus.ERROR));
    }
}

export function* listsSaga() {
    yield takeEvery(ListsActionType.FETCH_LISTS, fetchListsRequest);
    yield takeEvery(ListsActionType.FETCH_USER_LISTS, fetchUserListsRequest);
    yield takeEvery(ListsActionType.CREATE_LIST, createListRequest);
}
