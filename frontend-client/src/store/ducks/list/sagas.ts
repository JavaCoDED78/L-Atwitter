import { call, put, takeEvery } from "redux-saga/effects";

import {
  AddUserToListActionInterface,
  FetchListByIdActionInterface,
  FollowListActionInterface,
  ListActionType,
} from "./contracts/actionTypes";
import { setList, setListLoadingState } from "./actionCreators";
import { LoadingStatus } from "../../types";
import { ListsApi } from "../../../services/api/listsApi";
import { Lists } from "../lists/contracts/state";
import { setFollowList } from "../lists/actionCreators";

export function* fetchListByIdRequest({
  payload,
}: FetchListByIdActionInterface) {
  try {
    yield put(setListLoadingState(LoadingStatus.LOADING));
    const data: Lists = yield call(ListsApi.getListById, payload);
    yield put(setList(data));
  } catch (error) {
    yield put(setListLoadingState(LoadingStatus.ERROR));
  }
}

export function* followListRequest({ payload }: FollowListActionInterface) {
  try {
    yield put(setListLoadingState(LoadingStatus.LOADING));
    const data: Lists = yield call(ListsApi.followList, payload);
    yield put(setList(data));
    yield put(setFollowList(data));
  } catch (error) {
    yield put(setListLoadingState(LoadingStatus.ERROR));
  }
}

export function* addUserToListRequest({
  payload,
}: AddUserToListActionInterface) {
  try {
    yield put(setListLoadingState(LoadingStatus.LOADING));
    const data: Lists = yield call(ListsApi.addUserToList, payload);
    yield put(setList(data));
  } catch (error) {
    yield put(setListLoadingState(LoadingStatus.ERROR));
  }
}

export function* listSaga() {
  yield takeEvery(ListActionType.FETCH_LIST_BY_ID, fetchListByIdRequest);
  yield takeEvery(ListActionType.FOLLOW_LIST, followListRequest);
  yield takeEvery(ListActionType.ADD_USER_TO_LIST, addUserToListRequest);
}
