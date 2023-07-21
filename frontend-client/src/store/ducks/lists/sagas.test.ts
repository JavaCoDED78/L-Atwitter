import {
  createListRequest,
  fetchListsRequest,
  fetchPinnedListsRequest,
  fetchSimpleListsRequest,
  fetchTweetListsWhichUserInRequest,
  fetchUserListsByIdRequest,
  fetchUserListsRequest,
  followListRequest,
  listsSaga,
  pinListRequest,
  processUserToListsRequest,
  unfollowListRequest,
  unpinListRequest,
} from "./sagas";
import {
  createList,
  fetchSimpleLists,
  fetchUserListsById,
  followList,
  pinList,
  processUserToLists,
  setCreatedList,
  setFollowList,
  setLists,
  setLoadingState,
  setPinedList,
  setPinedListToUserList,
  setPinnedLists,
  setPinnedListsLoadingState,
  setSimpleLists,
  setSimpleListsLoadingState,
  setUnfollowList,
  setUnpinList,
  setUserLists,
  setUserListsLoadingState,
  unfollowList,
  unpinList,
} from "./actionCreators";
import { LoadingStatus } from "../../types";
import {
  testCall,
  testLoadingStatus,
  testSetResponse,
  testWatchSaga,
} from "../../../util/testHelper";
import { ListsApi } from "../../../services/api/listsApi";
import {
  ListResponse,
  ListUserResponse,
  PinnedListResponse,
  SimpleListResponse,
} from "../../types/lists";
import { AddLists, AddUserToListsRequest } from "./contracts/state";
import { updateFollowToFullList } from "../list/actionCreators";
import { updateFollowListDetail } from "../listDetail/actionCreators";
import { takeEvery } from "redux-saga/effects";
import { ListsActionType } from "./contracts/actionTypes";

describe("listsSaga:", () => {
  const mockListResponse = [{ id: 1 }, { id: 2 }] as ListResponse[];
  const mockListUserResponse = [{ id: 1 }, { id: 2 }] as ListUserResponse[];
  const mockPinnedListResponse = [{ id: 1 }, { id: 2 }] as PinnedListResponse[];

  describe("fetchListsRequest:", () => {
    const worker = fetchListsRequest();

    testLoadingStatus(worker, setLoadingState, LoadingStatus.LOADING);
    testCall(worker, ListsApi.getAllTweetLists);
    testSetResponse({
      worker: worker,
      mockData: mockListResponse,
      action: setLists,
      payload: mockListResponse,
      responseType: "ListResponse",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("fetchUserListsRequest:", () => {
    const worker = fetchUserListsRequest();

    testLoadingStatus(worker, setUserListsLoadingState, LoadingStatus.LOADING);
    testCall(worker, ListsApi.getUserTweetLists);
    testSetResponse({
      worker: worker,
      mockData: mockListUserResponse,
      action: setUserLists,
      payload: mockListUserResponse,
      responseType: "ListUserResponse",
    });
    testLoadingStatus(worker, setUserListsLoadingState, LoadingStatus.ERROR);
  });

  describe("fetchUserListsByIdRequest:", () => {
    const worker = fetchUserListsByIdRequest(fetchUserListsById(1));

    testLoadingStatus(worker, setLoadingState, LoadingStatus.LOADING);
    testCall(worker, ListsApi.getUserTweetListsById, 1);
    testSetResponse({
      worker: worker,
      mockData: mockListResponse,
      action: setLists,
      payload: mockListResponse,
      responseType: "ListUserResponse",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("fetchTweetListsWhichUserInRequest:", () => {
    const worker = fetchTweetListsWhichUserInRequest();

    testLoadingStatus(worker, setLoadingState, LoadingStatus.LOADING);
    testCall(worker, ListsApi.getTweetListsWhichUserIn);
    testSetResponse({
      worker: worker,
      mockData: mockListResponse,
      action: setLists,
      payload: mockListResponse,
      responseType: "ListResponse",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("fetchPinnedListsRequest:", () => {
    const worker = fetchPinnedListsRequest();

    testLoadingStatus(
      worker,
      setPinnedListsLoadingState,
      LoadingStatus.LOADING
    );
    testCall(worker, ListsApi.getUserPinnedLists);
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: setPinnedLists,
      payload: mockPinnedListResponse,
      responseType: "PinnedListResponse",
    });
    testLoadingStatus(worker, setPinnedListsLoadingState, LoadingStatus.ERROR);
  });

  describe("fetchSimpleListsRequest:", () => {
    const mockSimpleListResponse = [
      { id: 1 },
      { id: 2 },
    ] as SimpleListResponse[];
    const worker = fetchSimpleListsRequest(fetchSimpleLists(1));

    testLoadingStatus(
      worker,
      setSimpleListsLoadingState,
      LoadingStatus.LOADING
    );
    testCall(worker, ListsApi.getListsToAddUser, 1);
    testSetResponse({
      worker: worker,
      mockData: mockSimpleListResponse,
      action: setSimpleLists,
      payload: mockSimpleListResponse,
      responseType: "SimpleListResponse",
    });
    testLoadingStatus(worker, setSimpleListsLoadingState, LoadingStatus.ERROR);
  });

  describe("createListRequest:", () => {
    const mockListUserResponse = { id: 1 } as ListUserResponse;
    const mockAddLists = {
      name: "test",
      description: "test",
      isPrivate: true,
    } as AddLists;
    const worker = createListRequest(createList(mockAddLists));

    testLoadingStatus(worker, setLoadingState, LoadingStatus.LOADING);
    testCall(worker, ListsApi.createTweetList, mockAddLists);
    testSetResponse({
      worker: worker,
      mockData: mockListUserResponse,
      action: setCreatedList,
      payload: mockListUserResponse,
      responseType: "ListUserResponse",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("pinListRequest:", () => {
    const mockPinnedListResponse = { id: 1 } as PinnedListResponse;
    const worker = pinListRequest(pinList(1));

    testCall(worker, ListsApi.pinList, 1);
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: setPinedList,
      payload: mockPinnedListResponse,
      responseType: "PinnedListResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: setPinedListToUserList,
      payload: mockPinnedListResponse,
      responseType: "PinnedListResponse",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("unpinListRequest:", () => {
    const mockPinnedListResponse = { id: 1 } as PinnedListResponse;
    const worker = unpinListRequest(unpinList(1));

    testCall(worker, ListsApi.pinList, 1);
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: setUnpinList,
      payload: mockPinnedListResponse,
      responseType: "PinnedListResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: setPinedListToUserList,
      payload: mockPinnedListResponse,
      responseType: "PinnedListResponse",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("followListRequest:", () => {
    const mockListUserResponse = { id: 1 } as ListUserResponse;
    const worker = followListRequest(followList(1));

    testCall(worker, ListsApi.followList, 1);
    testSetResponse({
      worker: worker,
      mockData: mockListUserResponse,
      action: setFollowList,
      payload: mockListUserResponse,
      responseType: "ListUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: updateFollowToFullList,
      payload: true,
      responseType: "true",
    });
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: updateFollowListDetail,
      payload: true,
      responseType: "true",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("unfollowListRequest:", () => {
    const mockListUserResponse = { id: 1 } as ListUserResponse;
    const worker = unfollowListRequest(unfollowList(1));

    testCall(worker, ListsApi.followList, 1);
    testSetResponse({
      worker: worker,
      mockData: mockListUserResponse,
      action: setUnfollowList,
      payload: mockListUserResponse,
      responseType: "ListUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: updateFollowToFullList,
      payload: false,
      responseType: "false",
    });
    testSetResponse({
      worker: worker,
      mockData: mockPinnedListResponse,
      action: updateFollowListDetail,
      payload: false,
      responseType: "false",
    });
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  describe("processUserToListsRequest:", () => {
    const mockAddUserToListsRequest = { userId: 1 } as AddUserToListsRequest;
    const worker = processUserToListsRequest(
      processUserToLists(mockAddUserToListsRequest)
    );

    testCall(worker, ListsApi.addUserToLists, mockAddUserToListsRequest);
    testLoadingStatus(worker, setLoadingState, LoadingStatus.ERROR);
  });

  testWatchSaga(
    listsSaga,
    [
      { actionType: ListsActionType.FETCH_LISTS, workSaga: fetchListsRequest },
      {
        actionType: ListsActionType.FETCH_USER_LISTS,
        workSaga: fetchUserListsRequest,
      },
      {
        actionType: ListsActionType.FETCH_USER_LISTS_BY_ID,
        workSaga: fetchUserListsByIdRequest,
      },
      {
        actionType: ListsActionType.FETCH_USER_LISTS_BY_ID,
        workSaga: fetchTweetListsWhichUserInRequest,
      },
      {
        actionType: ListsActionType.FETCH_PINNED_LISTS,
        workSaga: fetchPinnedListsRequest,
      },
      {
        actionType: ListsActionType.FETCH_SIMPLE_LISTS,
        workSaga: fetchSimpleListsRequest,
      },
      { actionType: ListsActionType.CREATE_LIST, workSaga: createListRequest },
      { actionType: ListsActionType.PIN_LIST, workSaga: pinListRequest },
      { actionType: ListsActionType.UNPIN_LIST, workSaga: unpinListRequest },
      { actionType: ListsActionType.FOLLOW_LIST, workSaga: followListRequest },
      {
        actionType: ListsActionType.UNFOLLOW_LIST,
        workSaga: unfollowListRequest,
      },
      {
        actionType: ListsActionType.PROCESS_USER_TO_LISTS,
        workSaga: processUserToListsRequest,
      },
    ],
    takeEvery
  );
});
