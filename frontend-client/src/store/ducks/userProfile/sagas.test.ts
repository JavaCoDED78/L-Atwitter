import {
  fetchChatParticipantRequest,
  fetchImagesRequest,
  fetchUserRequest,
  processSubscribeRequest,
  userProfileSaga,
} from "./sagas";
import {
  fetchChatParticipant,
  fetchImages,
  fetchUserProfile,
  processSubscribe,
  setImages,
  setImagesLoadingStatus,
  setSubscribeToUserProfile,
  setUserProfile,
  setUserProfileLoadingState,
} from "./actionCreators";
import { LoadingStatus } from "../../types";
import {
  testCall,
  testLoadingStatus,
  testSetResponse,
  testWatchSaga,
} from "../../../util/testHelper";
import { UserProfileResponse } from "../../types/user";
import { UserApi } from "../../../services/api/userApi";
import { ChatApi } from "../../../services/api/chatApi";
import { TweetImageResponse } from "../../types/tweet";
import { UserProfileActionsType } from "./contracts/actionTypes";

describe("userProfileSaga:", () => {
  const mockUserProfileResponse = { id: 1 } as UserProfileResponse;

  describe("fetchUserRequest:", () => {
    const worker = fetchUserRequest(fetchUserProfile(1));

    testLoadingStatus(
      worker,
      setUserProfileLoadingState,
      LoadingStatus.LOADING
    );
    testCall(worker, UserApi.getUserInfo, 1);
    testSetResponse({
      worker: worker,
      mockData: mockUserProfileResponse,
      action: setUserProfile,
      payload: mockUserProfileResponse,
      responseType: "UserProfileResponse",
    });
    testLoadingStatus(worker, setUserProfileLoadingState, LoadingStatus.ERROR);
  });

  describe("processSubscribeRequest:", () => {
    const worker = processSubscribeRequest(processSubscribe(1));

    testCall(worker, UserApi.processSubscribeToNotifications, 1);
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setSubscribeToUserProfile,
      payload: true,
      responseType: "boolean",
    });
    testLoadingStatus(worker, setUserProfileLoadingState, LoadingStatus.ERROR);
  });

  describe("fetchChatParticipantRequest:", () => {
    const worker = fetchChatParticipantRequest(
      fetchChatParticipant({ participantId: 1, chatId: 1 })
    );

    testLoadingStatus(
      worker,
      setUserProfileLoadingState,
      LoadingStatus.LOADING
    );
    testCall(worker, ChatApi.getParticipant, { participantId: 1, chatId: 1 });
    testSetResponse({
      worker: worker,
      mockData: mockUserProfileResponse,
      action: setUserProfile,
      payload: mockUserProfileResponse,
      responseType: "UserProfileResponse",
    });
    testLoadingStatus(worker, setUserProfileLoadingState, LoadingStatus.ERROR);
  });

  describe("fetchImagesRequest:", () => {
    const mockTweetImageResponse = [
      { tweetId: 1, imageId: 1 },
    ] as TweetImageResponse[];
    const worker = fetchImagesRequest(fetchImages(1));

    testLoadingStatus(worker, setImagesLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, UserApi.getUserTweetImages, 1);
    testSetResponse({
      worker: worker,
      mockData: mockTweetImageResponse,
      action: setImages,
      payload: mockTweetImageResponse,
      responseType: "TweetImageResponse",
    });
    testLoadingStatus(worker, setImagesLoadingStatus, LoadingStatus.ERROR);
  });

  testWatchSaga(userProfileSaga, [
    {
      actionType: UserProfileActionsType.FETCH_USER,
      workSaga: fetchUserRequest,
    },
    {
      actionType: UserProfileActionsType.PROCESS_SUBSCRIBE,
      workSaga: processSubscribeRequest,
    },
    {
      actionType: UserProfileActionsType.FETCH_CHAT_PARTICIPANT,
      workSaga: fetchChatParticipantRequest,
    },
    {
      actionType: UserProfileActionsType.FETCH_IMAGES,
      workSaga: fetchImagesRequest,
    },
  ]);
});
