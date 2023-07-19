import produce, {Draft} from 'immer';

import {User, UserState} from "./contracts/state";
import {UserActions, UserActionsType} from './contracts/actionTypes';
import {LoadingStatus} from '../../types';

const initialUserState: UserState = {
    data: undefined,
    status: LoadingStatus.NEVER,
};

export const userReducer = produce((draft: Draft<UserState>, action: UserActions) => {

    switch (action.type) {
        case UserActionsType.SET_USER_DATA: //+
            draft.data = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SIGN_OUT: // +
            draft.status = LoadingStatus.LOADED;
            draft.data = undefined;
            break;

        case UserActionsType.SET_UNREAD_MESSAGE: // +
            draft.data!.unreadMessagesSize = draft.data!.unreadMessagesSize + 1;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_USERNAME: // +
            draft.data!.username = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_EMAIL: // +
            draft.data!.email = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_PHONE: // +
            draft.data!.countryCode = action.payload.countryCode;
            draft.data!.phone = action.payload.phone;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_COUNTRY: // +
            draft.data!.country = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_GENDER: // +
            draft.data!.gender = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_LANGUAGE: // +
            draft.data!.language = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_DIRECT: // +
            draft.data!.mutedDirectMessages = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_PRIVATE_PROFILE: // +
            draft.data!.privateProfile = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_COLOR_SCHEME: // +
            draft.data!.colorScheme = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_BACKGROUND_COLOR: // +
            draft.data!.backgroundColor = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_NEW_NOTIFICATION: // +
            draft.data!.notificationsCount = draft.data!.notificationsCount + 1;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_FOLLOWERS_SIZE: //+
            draft.data!.followersSize = draft.data!.followersSize + 1;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_PROFILE_STARTED: //+
            draft.data!.profileStarted = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_PIN_TWEET_ID: //+
            draft.data!.pinnedTweetId = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_READ_MESSAGE: //+
            draft.data!.unreadMessagesSize = action.payload;
            draft.status = LoadingStatus.LOADED;
            break;

        case UserActionsType.SET_USER_LOADING_STATE: //+
            draft.status = action.payload;
            break;

        default:
            break;
    }
}, initialUserState);
