import {
  deleteListRequest,
  editListRequest,
  fetchListByIdRequest,
  listSaga,
} from "./sagas";
import {
  deleteList,
  editList,
  fetchListById,
  setList,
  setListLoadingState,
} from "./actionCreators";
import { LoadingStatus } from "../../types";
import {
  testCall,
  testLoadingStatus,
  testSetResponse,
  testWatchSaga,
} from "../../../util/testHelper";
import { ListsApi } from "../../../services/api/listsApi";
import { BaseListResponse } from "../../types/lists";
import { EditListsRequest } from "./contracts/state";
import { ListActionType } from "./contracts/actionTypes";
import { takeEvery } from "redux-saga/effects";

describe("listSaga:", () => {
  const mockBaseListResponse = { id: 1 } as BaseListResponse;

  describe("fetchListByIdRequest:", () => {
    const worker = fetchListByIdRequest(fetchListById(1));

    testLoadingStatus(worker, setListLoadingState, LoadingStatus.LOADING);
    testCall(worker, ListsApi.getListById, 1);
    testSetResponse({
      worker: worker,
      mockData: mockBaseListResponse,
      action: setList,
      payload: mockBaseListResponse,
      responseType: "BaseListResponse",
    });
    testLoadingStatus(worker, setListLoadingState, LoadingStatus.ERROR);
  });

  describe("deleteListRequest:", () => {
    const worker = deleteListRequest(deleteList(1));

    testCall(worker, ListsApi.deleteList, 1);
    testLoadingStatus(worker, setListLoadingState, LoadingStatus.ERROR);
  });

  describe("editListRequest:", () => {
    const mockEditListsRequest = { id: 1, name: "text" } as EditListsRequest;
    const worker = editListRequest(editList(mockEditListsRequest));

    testLoadingStatus(worker, setListLoadingState, LoadingStatus.LOADING);
    testCall(worker, ListsApi.editList, mockEditListsRequest);
    testSetResponse({
      worker: worker,
      mockData: mockBaseListResponse,
      action: setList,
      payload: mockBaseListResponse,
      responseType: "BaseListResponse",
    });
    testLoadingStatus(worker, setListLoadingState, LoadingStatus.ERROR);
  });

  testWatchSaga(
    listSaga,
    [
      {
        actionType: ListActionType.FETCH_LIST_BY_ID,
        workSaga: fetchListByIdRequest,
      },
      { actionType: ListActionType.DELETE_LIST, workSaga: deleteListRequest },
      { actionType: ListActionType.EDIT_LIST, workSaga: editListRequest },
    ],
    takeEvery
  );
});
