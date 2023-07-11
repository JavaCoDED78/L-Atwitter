import {User, UserState} from "./contracts/state";
import {
    AddTweetToBookmarksActionInterface,
    FetchPinTweetActionInterface,
    FetchReadMessagesActionInterface,
    FetchSignInActionInterface,
    FetchSignUpActionInterface,
    FetchUnpinTweetActionInterface,
    FetchUserDataActionInterface,
    FollowActionInterface,
    FollowUserActionInterface,
    SetNewNotificationActionInterface,
    SetUnreadMessageActionInterface,
    SetUserDataActionInterface,
    SetUserLoadingStateActionInterface,
    SignOutActionInterface,
    StartUseTwitterActionInterface,
    UnfollowActionInterface,
    UnfollowUserActionInterface,
    UserActionsType
} from "./contracts/actionTypes";
import {RegistrationProps} from "../../../pages/RegistrationModal/SetPasswordModal/SetPasswordModal";
import {LoginProps} from "../../../pages/Login/Login";
import {ChatMessage} from "../chatMessages/contracts/state";

export const setUserData = (payload: UserState["data"]): SetUserDataActionInterface => ({
    type: UserActionsType.SET_USER_DATA,
    payload
});

export const signOut = (): SignOutActionInterface => ({
    type: UserActionsType.SIGN_OUT,
});

export const fetchSignIn = (payload: LoginProps): FetchSignInActionInterface => ({
    type: UserActionsType.FETCH_SIGN_IN,
    payload,
});

export const fetchSignUp = (payload: RegistrationProps): FetchSignUpActionInterface => ({
    type: UserActionsType.FETCH_SIGN_UP,
    payload,
});

export const fetchUserData = (): FetchUserDataActionInterface => ({
    type: UserActionsType.FETCH_USER_DATA,
});

export const setUserLoadingStatus = (payload: UserState["status"]): SetUserLoadingStateActionInterface => ({
    type: UserActionsType.SET_USER_LOADING_STATE,
    payload
});

export const followUser = (payload: User): FollowUserActionInterface => ({
    type: UserActionsType.FOLLOW_USER,
    payload,
});

export const unfollowUser = (payload: User): UnfollowUserActionInterface => ({
    type: UserActionsType.UNFOLLOW_USER,
    payload,
});

export const follow = (payload: User): FollowActionInterface => ({
    type: UserActionsType.FOLLOW,
    payload,
});

export const unfollow = (payload: User): UnfollowActionInterface => ({
    type: UserActionsType.UNFOLLOW,
    payload,
});

export const fetchPinTweet = (payload: string): FetchPinTweetActionInterface => ({
    type: UserActionsType.FETCH_PIN_TWEET,
    payload,
});

export const fetchUnpinTweet = (payload: string): FetchUnpinTweetActionInterface => ({
    type: UserActionsType.FETCH_UNPIN_TWEET,
    payload,
});

export const addTweetToBookmarks = (payload: string): AddTweetToBookmarksActionInterface => ({
    type: UserActionsType.ADD_TWEET_TO_BOOKMARKS,
    payload,
});

export const startUseTwitter = (payload: number): StartUseTwitterActionInterface => ({
    type: UserActionsType.START_USE_TWITTER,
    payload,
});

export const fetchReadMessages = (payload: number): FetchReadMessagesActionInterface => ({
    type: UserActionsType.FETCH_READ_MESSAGES,
    payload,
});

export const setUnreadMessage = (payload: ChatMessage): SetUnreadMessageActionInterface => ({
    type: UserActionsType.SET_UNREAD_MESSAGE,
    payload,
});

export const setNewNotification = (): SetNewNotificationActionInterface => ({
    type: UserActionsType.SET_NEW_NOTIFICATION,
});
