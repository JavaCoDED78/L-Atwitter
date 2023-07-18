import produce, {Draft} from 'immer';

import {LoadingStatus} from '../../types';
import {UsersSearchState} from "./contracts/state";
import {UsersSearchActions, UsersSearchActionsType} from "./contracts/actionTypes";

const initialUsersState: UsersSearchState = {
    users: [],
    loadingState: LoadingStatus.LOADING,
};

export const usersSearchReducer = produce((draft: Draft<UsersSearchState>, action: UsersSearchActions) => {
    switch (action.type) {
        case UsersSearchActionsType.SET_USERS:
            draft.users = action.payload;
            draft.loadingState = LoadingStatus.LOADED;
            break;

        case UsersSearchActionsType.RESET_USERS_STATE:
            draft.users = [];
            draft.loadingState = LoadingStatus.LOADING;
            break;

        case UsersSearchActionsType.SET_USERS_LOADING_STATE:
            draft.loadingState = action.payload;
            break;

        default:
            break;
    }
}, initialUsersState);
