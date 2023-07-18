import {Action} from "redux";

import {User} from "../../user/contracts/state";
import {LoadingStatus} from "../../../types";

export enum UsersActionsType {
    SET_USERS = 'users/SET_USERS',
    SET_UPDATED_USER = 'users/SET_UPDATED_USER',
    FETCH_USERS = 'users/FETCH_USERS',
    FETCH_RELEVANT_USERS = 'users/FETCH_RELEVANT_USERS',
    FETCH_BLOCKED_USERS = 'users/FETCH_BLOCKED_USERS',
    FETCH_MUTED_USERS = 'users/FETCH_MUTED_USERS',
    RESET_USERS_STATE = 'users/RESET_USERS_STATE',
    SET_USER_LOADING_STATE = 'users/SET_USER_LOADING_STATE',
}

export interface SetUsersActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.SET_USERS;
    payload: User[];
}

export interface SetUpdatedUserActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.SET_UPDATED_USER;
    payload: User;
}

export interface FetchUsersActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.FETCH_USERS;
}

export interface FetchRelevantUsersActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.FETCH_RELEVANT_USERS;
}

export interface FetchBlockedUsersActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.FETCH_BLOCKED_USERS;
}

export interface FetchMutedUsersActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.FETCH_MUTED_USERS;
}

export interface ResetUsersStateActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.RESET_USERS_STATE;
}

export interface SetUsersLoadingStatusActionInterface extends Action<UsersActionsType> {
    type: UsersActionsType.SET_USER_LOADING_STATE;
    payload: LoadingStatus;
}
export type UsersActions =
    | SetUsersActionInterface
    | SetUpdatedUserActionInterface
    | ResetUsersStateActionInterface
    | SetUsersLoadingStatusActionInterface;
