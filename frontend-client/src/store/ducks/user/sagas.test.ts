import {
  fetchPinTweetRequest,
  fetchReadMessagesRequest,
  fetchSignInRequest,
  fetchSignUpRequest,
  fetchUserDataRequest,
  processFollowRequests,
  processFollowUserRequest,
  processUserToBlocklistRequest,
  processUserToMuteListRequest,
  startUseTwitterRequest,
  updateBackgroundColorRequest,
  updateColorSchemeRequest,
  updateCountryRequest,
  updateDirectRequest,
  updateEmailRequest,
  updateGenderRequest,
  updateLanguageRequest,
  updatePhoneRequest,
  updatePrivateProfileRequest,
  updateUserDataRequest,
  updateUsernameRequest,
  userSaga,
} from "./sagas";
import {
  fetchPinTweet,
  fetchReadMessages,
  fetchSignIn,
  fetchSignUp,
  followUser,
  processFollowRequest,
  processUserToBlocklist,
  processUserToMuteList,
  setBackgroundColor,
  setColorScheme,
  setCountry,
  setDirect,
  setEmail,
  setGender,
  setLanguage,
  setPhone,
  setPinTweetId,
  setPrivateProfile,
  setProfileStarted,
  setReadMessage,
  setUserData,
  setUserFollowing,
  setUserLoadingStatus,
  setUsername,
  startUseTwitter,
  updateBackgroundColor,
  updateColorScheme,
  updateCountry,
  updateDirect,
  updatedUserData,
  updateEmail,
  updateGender,
  updateLanguage,
  updatePhone,
  updatePrivateProfile,
  updateUsername,
} from "./actionCreators";

import { LoadingStatus } from "../../types";
import {
  testCall,
  testLoadingStatus,
  testSetResponse,
  testWatchSaga,
} from "../../../util/testHelper";
import { UserApi } from "../../../services/api/userApi";
import { AuthUserResponse, UserProfileResponse } from "../../types/user";
import { Settings, UserRequest } from "./contracts/state";
import { AuthApi } from "../../../services/api/authApi";
import { AuthenticationResponse } from "../../types/auth";
import { LoginProps } from "../../../pages/Login/Login";
import { RegistrationProps } from "../../../pages/RegistrationModal/SetPasswordModal/SetPasswordModal";
import { NotificationUserResponse } from "../../types/notification";
import {
  setBlockedToTweetsState,
  setFollowToTweetsState,
  setMutedToTweetsState,
} from "../tweets/actionCreators";
import {
  setBlockedUsersTweetState,
  setFollowToUsersTweetState,
  setMutedUsersTweetState,
} from "../userTweets/actionCreators";
import {
  setBlocked,
  setFollowRequestToUserProfile,
  setFollowToUserProfile,
  setMuted,
} from "../userProfile/actionCreators";
import {
  setBlockUserDetail,
  setFollowRequestToUserDetail,
  setFollowToUserDetail,
} from "../userDetail/actionCreators";
import {
  setBlockedUsersState,
  setFollowRequestToUsers,
  setFollowToUsersState,
  setMutedUsersState,
} from "../users/actionCreators";
import {
  setBlockUsersSearchState,
  setFollowRequestToUsersSearchState,
  setFollowToUsersSearchState,
} from "../usersSearch/actionCreators";
import {
  setBlockedToTweetState,
  setFollowToTweetState,
  setMutedToTweetState,
} from "../tweet/actionCreators";
import {
  setBlockedNotificationInfo,
  setFollowRequestToNotificationInfo,
  setFollowToNotificationInfo,
} from "../notifications/actionCreators";
import { ChatApi } from "../../../services/api/chatApi";
import { UserSettingsApi } from "../../../services/api/userSettingsApi";
import {
  BackgroundTheme,
  ColorScheme,
} from "../../../pages/Settings/AccessibilityDisplayLanguages/Display/Display";
import {
  setBlockedUser,
  setMutedUser,
} from "../blockedAndMutedUsers/actionCreators";
import { UserActionsType } from "./contracts/actionTypes";

describe("userSaga:", () => {
  const mockAuthUser = { id: 1, email: "test@test.test" } as AuthUserResponse;
  const mockAuthentication = {
    user: mockAuthUser,
    token: "test",
  } as AuthenticationResponse;

  describe("updateUserDataRequest:", () => {
    const mockUserRequest = {
      username: "test",
      location: "test",
    } as UserRequest;
    const worker = updateUserDataRequest(updatedUserData(mockUserRequest));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, UserApi.updateUserProfile, mockUserRequest);
    testSetResponse({
      worker: worker,
      mockData: mockAuthUser,
      action: setUserData,
      payload: mockAuthUser,
      responseType: "AuthUserResponse",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("fetchSignInRequest:", () => {
    const mockLoginProps = {
      email: "test@test.test",
      password: "test",
    } as LoginProps;
    const worker = fetchSignInRequest(fetchSignIn(mockLoginProps));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, AuthApi.signIn, mockLoginProps);
    testSetResponse({
      worker: worker,
      mockData: mockAuthentication,
      action: setUserData,
      payload: mockAuthUser,
      responseType: "AuthenticationResponse",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("fetchSignUpRequest:", () => {
    const mockRegistrationProps = {
      email: "test@test.test",
      password: "test",
    } as RegistrationProps;
    const worker = fetchSignUpRequest(fetchSignUp(mockRegistrationProps));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, AuthApi.endRegistration, mockRegistrationProps);
    testSetResponse({
      worker: worker,
      mockData: mockAuthentication,
      action: setUserData,
      payload: mockAuthUser,
      responseType: "AuthenticationResponse",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("fetchUserDataRequest:", () => {
    const worker = fetchUserDataRequest();

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, AuthApi.getMe);
    testSetResponse({
      worker: worker,
      mockData: mockAuthentication,
      action: setUserData,
      payload: mockAuthUser,
      responseType: "AuthenticationResponse",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("processFollowUserRequest:", () => {
    const mockNotificationUserResponse = {
      id: 1,
      isFollower: true,
    } as NotificationUserResponse;
    const mockPayload = { userId: 1, tweetId: 1, isFollower: true };
    const worker = processFollowUserRequest(
      followUser({ userId: 1, tweetId: 1 })
    );

    testCall(worker, UserApi.follow, 1);
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToTweetsState,
      payload: mockPayload,
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToUsersTweetState,
      payload: mockPayload,
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setUserFollowing,
      payload: true,
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToUserProfile,
      payload: true,
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToUserDetail,
      payload: true,
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToUsersState,
      payload: {
        userId: 1,
        isFollower: true,
      },
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToUsersSearchState,
      payload: {
        userId: 1,
        isFollower: true,
      },
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToTweetState,
      payload: true,
      responseType: "NotificationUserResponse",
    });
    testSetResponse({
      worker: worker,
      mockData: mockNotificationUserResponse,
      action: setFollowToNotificationInfo,
      payload: true,
      responseType: "NotificationUserResponse",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("startUseTwitterRequest:", () => {
    const worker = startUseTwitterRequest(startUseTwitter(1));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, UserApi.startUseTwitter, 1, true);
    testSetResponse({
      worker: worker,
      mockData: 1,
      action: setProfileStarted,
      payload: 1,
      responseType: "boolean",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("fetchPinTweetRequest:", () => {
    const worker = fetchPinTweetRequest(fetchPinTweet(1));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, UserApi.pinTweet, 1, 1);
    testSetResponse({
      worker: worker,
      mockData: 1,
      action: setPinTweetId,
      payload: 1,
      responseType: "number",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("fetchReadMessagesRequest:", () => {
    const worker = fetchReadMessagesRequest(fetchReadMessages(1));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(worker, ChatApi.readChatMessages, 1, 1);
    testSetResponse({
      worker: worker,
      mockData: 1,
      action: setReadMessage,
      payload: 1,
      responseType: "number",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateUsernameRequest:", () => {
    const worker = updateUsernameRequest(updateUsername({ username: "test" }));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateUsername,
      { username: "test" },
      "test"
    );
    testSetResponse({
      worker: worker,
      mockData: "test",
      action: setUsername,
      payload: "test",
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateEmailRequest:", () => {
    const worker = updateEmailRequest(updateEmail({ email: "test@test.test" }));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateEmail,
      { email: "test@test.test" },
      "test@test.test"
    );
    testSetResponse({
      worker: worker,
      mockData: mockAuthentication,
      action: setEmail,
      payload: "test@test.test",
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updatePhoneRequest:", () => {
    const mockPhoneSettings = { countryCode: "US", phone: 12345 };
    const worker = updatePhoneRequest(updatePhone(mockPhoneSettings));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updatePhone,
      mockPhoneSettings,
      mockPhoneSettings
    );
    testSetResponse({
      worker: worker,
      mockData: mockPhoneSettings,
      action: setPhone,
      payload: mockPhoneSettings,
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateCountryRequest:", () => {
    const worker = updateCountryRequest(updateCountry({ country: "test" }));

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateCountry,
      { country: "test" },
      "test"
    );
    testSetResponse({
      worker: worker,
      mockData: "test",
      action: setCountry,
      payload: "test",
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateGenderRequest:", () => {
    const worker = updateGenderRequest(
      updateGender({ updateGender: "testGender" } as Settings)
    );

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateGender,
      { updateGender: "testGender" },
      "testGender"
    );
    testSetResponse({
      worker: worker,
      mockData: "testGender",
      action: setGender,
      payload: "testGender",
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateLanguageRequest:", () => {
    const worker = updateLanguageRequest(
      updateLanguage({ language: "english" })
    );

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateLanguage,
      { language: "english" },
      "english"
    );
    testSetResponse({
      worker: worker,
      mockData: "english",
      action: setLanguage,
      payload: "english",
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateDirectRequest:", () => {
    const worker = updateDirectRequest(
      updateDirect({ mutedDirectMessages: true })
    );

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateDirectMessageRequests,
      { mutedDirectMessages: true },
      true
    );
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setDirect,
      payload: true,
      responseType: "boolean",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updatePrivateProfileRequest:", () => {
    const worker = updatePrivateProfileRequest(
      updatePrivateProfile({ privateProfile: true })
    );

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updatePrivateProfile,
      { privateProfile: true },
      true
    );
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setPrivateProfile,
      payload: true,
      responseType: "boolean",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateColorSchemeRequest:", () => {
    const worker = updateColorSchemeRequest(
      updateColorScheme({ colorScheme: ColorScheme.BLUE })
    );

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateColorScheme,
      { colorScheme: ColorScheme.BLUE },
      "BLUE"
    );
    testSetResponse({
      worker: worker,
      mockData: "BLUE",
      action: setColorScheme,
      payload: "BLUE",
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("updateBackgroundColorRequest:", () => {
    const worker = updateBackgroundColorRequest(
      updateBackgroundColor({ backgroundColor: BackgroundTheme.DIM })
    );

    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.LOADING);
    testCall(
      worker,
      UserSettingsApi.updateBackgroundColor,
      { backgroundColor: BackgroundTheme.DIM },
      "DIM"
    );
    testSetResponse({
      worker: worker,
      mockData: "DIM",
      action: setBackgroundColor,
      payload: "DIM",
      responseType: "string",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("processUserToBlocklistRequest:", () => {
    const mockPayload = { userId: 1, tweetId: 1, isUserBlocked: true };
    const worker = processUserToBlocklistRequest(
      processUserToBlocklist({ userId: 1, tweetId: 1 })
    );

    testCall(worker, UserApi.processBlockList, 1, true);
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockedToTweetsState,
      payload: mockPayload,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockedUsersTweetState,
      payload: mockPayload,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlocked,
      payload: true,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockUserDetail,
      payload: true,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockedUser,
      payload: { userId: 1, isUserBlocked: true },
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockedUsersState,
      payload: { userId: 1, isUserBlocked: true },
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockUsersSearchState,
      payload: { userId: 1, isUserBlocked: true },
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockedToTweetState,
      payload: true,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setBlockedNotificationInfo,
      payload: true,
      responseType: "boolean",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("processUserToMuteListRequest:", () => {
    const mockPayload = { userId: 1, tweetId: 1, isUserMuted: true };
    const worker = processUserToMuteListRequest(
      processUserToMuteList({ userId: 1, tweetId: 1 })
    );

    testCall(worker, UserApi.processMutedList, 1, true);
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setMutedToTweetsState,
      payload: mockPayload,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setMutedUsersTweetState,
      payload: mockPayload,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setMuted,
      payload: true,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setMutedUser,
      payload: { userId: 1, isUserMuted: true },
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setMutedUsersState,
      payload: { userId: 1, isUserMuted: true },
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: true,
      action: setMutedToTweetState,
      payload: true,
      responseType: "boolean",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  describe("processFollowRequests:", () => {
    const mockUserProfileResponse = {
      id: 1,
      isWaitingForApprove: true,
    } as UserProfileResponse;
    const mockPayload = { userId: 1, isWaitingForApprove: true };
    const worker = processFollowRequests(processFollowRequest(1));

    testCall(
      worker,
      UserApi.processFollowRequestToPrivateProfile,
      1,
      mockUserProfileResponse
    );
    testSetResponse({
      worker: worker,
      mockData: mockUserProfileResponse,
      action: setFollowRequestToUserProfile,
      payload: true,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: mockUserProfileResponse,
      action: setFollowRequestToUserDetail,
      payload: true,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: mockUserProfileResponse,
      action: setFollowRequestToUsers,
      payload: mockPayload,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: mockUserProfileResponse,
      action: setFollowRequestToUsersSearchState,
      payload: mockPayload,
      responseType: "boolean",
    });
    testSetResponse({
      worker: worker,
      mockData: mockUserProfileResponse,
      action: setFollowRequestToNotificationInfo,
      payload: true,
      responseType: "boolean",
    });
    testLoadingStatus(worker, setUserLoadingStatus, LoadingStatus.ERROR);
  });

  testWatchSaga(userSaga, [
    {
      actionType: UserActionsType.UPDATE_USER_DATA,
      workSaga: updateUserDataRequest,
    },
    { actionType: UserActionsType.FETCH_SIGN_IN, workSaga: fetchSignInRequest },
    { actionType: UserActionsType.FETCH_SIGN_UP, workSaga: fetchSignUpRequest },
    {
      actionType: UserActionsType.FETCH_USER_DATA,
      workSaga: fetchUserDataRequest,
    },
    {
      actionType: UserActionsType.FOLLOW_USER,
      workSaga: processFollowUserRequest,
    },
    {
      actionType: UserActionsType.UNFOLLOW_USER,
      workSaga: processFollowUserRequest,
    },
    {
      actionType: UserActionsType.START_USE_TWITTER,
      workSaga: startUseTwitterRequest,
    },
    {
      actionType: UserActionsType.FETCH_PIN_TWEET,
      workSaga: fetchPinTweetRequest,
    },
    {
      actionType: UserActionsType.FETCH_READ_MESSAGES,
      workSaga: fetchReadMessagesRequest,
    },
    {
      actionType: UserActionsType.UPDATE_USERNAME,
      workSaga: updateUsernameRequest,
    },
    { actionType: UserActionsType.UPDATE_EMAIL, workSaga: updateEmailRequest },
    { actionType: UserActionsType.UPDATE_PHONE, workSaga: updatePhoneRequest },
    {
      actionType: UserActionsType.UPDATE_COUNTRY,
      workSaga: updateCountryRequest,
    },
    {
      actionType: UserActionsType.UPDATE_GENDER,
      workSaga: updateGenderRequest,
    },
    {
      actionType: UserActionsType.UPDATE_LANGUAGE,
      workSaga: updateLanguageRequest,
    },
    {
      actionType: UserActionsType.UPDATE_DIRECT,
      workSaga: updateDirectRequest,
    },
    {
      actionType: UserActionsType.UPDATE_PRIVATE_PROFILE,
      workSaga: updatePrivateProfileRequest,
    },
    {
      actionType: UserActionsType.UPDATE_COLOR_SCHEME,
      workSaga: updateColorSchemeRequest,
    },
    {
      actionType: UserActionsType.UPDATE_BACKGROUND_COLOR,
      workSaga: updateBackgroundColorRequest,
    },
    {
      actionType: UserActionsType.PROCESS_USER_TO_BLOCKLIST,
      workSaga: processUserToBlocklistRequest,
    },
    {
      actionType: UserActionsType.PROCESS_USER_TO_MUTELIST,
      workSaga: processUserToMuteListRequest,
    },
    {
      actionType: UserActionsType.PROCESS_FOLLOW_REQUEST,
      workSaga: processFollowRequests,
    },
  ]);
});
