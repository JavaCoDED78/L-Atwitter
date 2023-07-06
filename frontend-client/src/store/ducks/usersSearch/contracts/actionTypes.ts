import {Action} from "redux";
import {User} from "../../user/contracts/state";
import {LoadingStatus} from "../../../types";

export enum UsersSearchActionsType {
    SET_USERS = 'usersSearch/SET_USERS',
    FETCH_USERS  = 'usersSearch/FETCH_USERS',
    SET_USER_LOADING_STATE = 'usersSearch/SET_USER_LOADING_STATE',
}

export interface SetUsersSearchActionInterface extends Action<UsersSearchActionsType> {
    type: UsersSearchActionsType.SET_USERS;
    payload: User[];
}

export interface FetchUsersSearchActionInterface extends Action<UsersSearchActionsType> {
    type: UsersSearchActionsType.FETCH_USERS;
}

export interface SetUsersSearchLoadingStatusActionInterface extends Action<UsersSearchActionsType> {
    type: UsersSearchActionsType.SET_USER_LOADING_STATE;
    payload: LoadingStatus;
}
export type UsersSearchActions =
    | SetUsersSearchActionInterface
    | SetUsersSearchLoadingStatusActionInterface;
