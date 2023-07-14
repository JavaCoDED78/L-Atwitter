import {call, put, takeLatest} from 'redux-saga/effects';

import {LoadingStatus} from '../../types';
import {
    FetchUserProfileActionInterface,
    FollowUserProfileActionInterface,
    UnfollowUserProfileActionInterface,
    UpdateUserDataActionInterface,
    UserProfileActionsType,
} from "./contracts/actionTypes";
import {User} from "../user/contracts/state";
import {UserApi} from "../../../services/api/userApi";
import {setUserProfile, setUserProfileLoadingState} from "./actionCreators";

export function* fetchUpdateUserDataRequest({payload}: UpdateUserDataActionInterface) {
    try {
        yield put(setUserProfileLoadingState(LoadingStatus.LOADING));
        const data: User = yield call(UserApi.updateUserProfile, payload);
        yield put(setUserProfile(data));
    } catch (error) {
        yield put(setUserProfileLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchUserRequest({payload}: FetchUserProfileActionInterface) {
    try {
        yield put(setUserProfileLoadingState(LoadingStatus.LOADING));
        const item: User = yield call(UserApi.getUserInfo, payload);
        yield put(setUserProfile(item));
    } catch (error) {
        yield put(setUserProfileLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchFollowUser({payload}: FollowUserProfileActionInterface) {
    try {
        const item: User = yield call(UserApi.follow, payload);
        yield put(setUserProfile(item));
    } catch (error) {
        yield put(setUserProfileLoadingState(LoadingStatus.ERROR));
    }
}

export function* fetchUnfollowUser({payload}: UnfollowUserProfileActionInterface) {
    try {
        const item: User = yield call(UserApi.follow, payload);
        yield put(setUserProfile(item));
    } catch (error) {
        yield put(setUserProfileLoadingState(LoadingStatus.ERROR));
    }
}

export function* userProfileSaga() {
    yield takeLatest(UserProfileActionsType.UPDATE_USER_DATA, fetchUpdateUserDataRequest);
    yield takeLatest(UserProfileActionsType.FETCH_USER, fetchUserRequest);
    yield takeLatest(UserProfileActionsType.FOLLOW_USER, fetchFollowUser);
    yield takeLatest(UserProfileActionsType.UNFOLLOW_USER, fetchUnfollowUser);
}
