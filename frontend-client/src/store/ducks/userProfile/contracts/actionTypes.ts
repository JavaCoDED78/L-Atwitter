import {Action} from "redux";
import {User} from "../../user/contracts/state";
import {LoadingStatus} from "../../../types";

export enum UserProfileActionsType {
    SET_USER = 'userProfile/SET_USER',
    FETCH_USER  = 'userProfile/FETCH_USER',
    FOLLOW  = 'userProfile/FOLLOW',
    UNFOLLOW  = 'userProfile/UNFOLLOW',
    FOLLOW_USER  = 'userProfile/FOLLOW_USER',
    UNFOLLOW_USER  = 'userProfile/UNFOLLOW_USER',
    SET_USER_LOADING_STATE = 'userProfile/SET_USER_LOADING_STATE',
}

export interface SetUserProfileActionInterface extends Action<UserProfileActionsType> {
    type: UserProfileActionsType.SET_USER;
    payload: User;
}

export interface FetchUserProfileActionInterface extends Action<UserProfileActionsType> {
    type: UserProfileActionsType.FETCH_USER;
    payload: string;
}

export interface FollowProfileActionInterface extends Action<UserProfileActionsType> {
    type: UserProfileActionsType.FOLLOW;
    payload: User;
}

export interface UnfollowProfileActionInterface extends Action<UserProfileActionsType> {
    type: UserProfileActionsType.UNFOLLOW;
    payload: User;
}

export interface FollowUserProfileActionInterface extends Action<UserProfileActionsType> {
    type: UserProfileActionsType.FOLLOW_USER;
    payload: User;
}

export interface UnfollowUserProfileActionInterface extends Action<UserProfileActionsType> {
    type: UserProfileActionsType.UNFOLLOW_USER;
    payload: User;
}

export interface SetUserProfileLoadingStatusActionInterface extends Action<UserProfileActionsType> {
    type: UserProfileActionsType.SET_USER_LOADING_STATE;
    payload: LoadingStatus;
}

export type UserProfileActions =
    | SetUserProfileActionInterface
    | FollowProfileActionInterface
    | UnfollowProfileActionInterface
    | SetUserProfileLoadingStatusActionInterface;
