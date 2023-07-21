import {
  fetchListFollowersRequest,
  fetchListMembersByUsernameRequest,
  fetchListMembersRequest,
  listMembersSaga,
  processListMemberRequest,
} from "./sagas";
import {
  fetchListFollowers,
  fetchListMembers,
  fetchListMembersByUsername,
  processUserToListMembers,
  setListMembers,
  setListSuggested,
  setLoadingMembersState,
  setLoadingSuggestedState,
  setUserToListMembers,
} from "./actionCreators";
import { LoadingStatus } from "../../types";
import {
  testLoadingStatus,
  testSetResponse,
  testWatchSaga,
} from "../../../util/testHelper";
import { ListsApi } from "../../../services/api/listsApi";
import { ListsOwnerMemberResponse } from "../../types/lists";
import { call } from "redux-saga/effects";
import { setMembersSize } from "../list/actionCreators";
import { ListMembersActionsType } from "./contracts/actionTypes";

describe("listMembersSaga:", () => {
  const mockListsOwnerMemberResponse = [
    { id: 1 },
    { id: 2 },
  ] as ListsOwnerMemberResponse[];

  describe("fetchListMembersRequest:", () => {
    const worker = fetchListMembersRequest(
      fetchListMembers({ listId: 1, listOwnerId: 1 })
    );

    testLoadingStatus(worker, setLoadingMembersState, LoadingStatus.LOADING);

    it("should call getListMembers", () => {
      const actualYield = worker.next().value;
      const expectedYield = call(ListsApi.getListMembers, 1, 1);

      expect(actualYield).toEqual(expectedYield);
    });
    testSetResponse({
      worker: worker,
      mockData: mockListsOwnerMemberResponse,
      action: setListMembers,
      payload: mockListsOwnerMemberResponse,
      responseType: "ListsOwnerMemberResponse",
    });
    testLoadingStatus(worker, setLoadingMembersState, LoadingStatus.ERROR);
  });

  describe("fetchListFollowersRequest:", () => {
    const worker = fetchListFollowersRequest(
      fetchListFollowers({ listId: 1, listOwnerId: 1 })
    );

    testLoadingStatus(worker, setLoadingMembersState, LoadingStatus.LOADING);

    it("should call getListFollowers", () => {
      const actualYield = worker.next().value;
      const expectedYield = call(ListsApi.getListFollowers, 1, 1);

      expect(actualYield).toEqual(expectedYield);
    });
    testSetResponse({
      worker: worker,
      mockData: mockListsOwnerMemberResponse,
      action: setListMembers,
      payload: mockListsOwnerMemberResponse,
      responseType: "ListsOwnerMemberResponse",
    });
    testLoadingStatus(worker, setLoadingMembersState, LoadingStatus.ERROR);
  });

  describe("fetchListMembersByUsernameRequest:", () => {
    const worker = fetchListMembersByUsernameRequest(
      fetchListMembersByUsername({ listId: 1, username: "test" })
    );

    testLoadingStatus(worker, setLoadingSuggestedState, LoadingStatus.LOADING);

    it("should call searchListMembersByUsername", () => {
      const actualYield = worker.next().value;
      const expectedYield = call(
        ListsApi.searchListMembersByUsername,
        1,
        "test"
      );

      expect(actualYield).toEqual(expectedYield);
    });
    testSetResponse({
      worker: worker,
      mockData: mockListsOwnerMemberResponse,
      action: setListSuggested,
      payload: mockListsOwnerMemberResponse,
      responseType: "ListsOwnerMemberResponse",
    });
    testLoadingStatus(worker, setLoadingSuggestedState, LoadingStatus.ERROR);
  });

  describe("processListMemberRequest:", () => {
    const worker = processListMemberRequest(
      processUserToListMembers({ userId: 1, listId: 1 })
    );

    testLoadingStatus(worker, setLoadingSuggestedState, LoadingStatus.LOADING);

    it("should call addUserToList", () => {
      const actualYield = worker.next().value;
      const expectedYield = call(ListsApi.addUserToList, 1, 1);

      expect(actualYield).toEqual(expectedYield);
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setUserToListMembers,
      payload: { userId: 1, isMember: true },
      responseType: "ListsOwnerMemberResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setMembersSize,
      payload: true,
      responseType: "ListsOwnerMemberResponse",
    });
    testLoadingStatus(worker, setLoadingSuggestedState, LoadingStatus.ERROR);
  });

  testWatchSaga(listMembersSaga, [
    {
      actionType: ListMembersActionsType.FETCH_LIST_MEMBERS,
      workSaga: fetchListMembersRequest,
    },
    {
      actionType: ListMembersActionsType.FETCH_LIST_FOLLOWERS,
      workSaga: fetchListFollowersRequest,
    },
    {
      actionType: ListMembersActionsType.FETCH_LIST_MEMBERS_BY_USERNAME,
      workSaga: fetchListMembersByUsernameRequest,
    },
    {
      actionType: ListMembersActionsType.PROCESS_USER_TO_LIST_MEMBERS,
      workSaga: processListMemberRequest,
    },
  ]);
});
