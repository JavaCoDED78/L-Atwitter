import {Image, SameFollowerResponse} from "./common";

export interface UserProfileResponse {
    id: number;
    fullName: string;
    username: string;
    location: string;
    about: string;
    website: string;
    country: string;
    birthday: string;
    tweetCount: number;
    mediaTweetCount: number;
    likeCount: number;
    notificationsCount: number;
    mutedDirectMessages: boolean;
    isPrivateProfile: boolean;
    avatar: Image;
    wallpaper: Image;
    pinnedTweetId: number;
    followersSize: number;
    followingSize: number;
    isUserMuted: boolean;
    isUserBlocked: boolean;
    isMyProfileBlocked: boolean;
    isWaitingForApprove: boolean;
    isFollower: boolean;
    isSubscriber: boolean;
    sameFollowers: SameFollowerResponse;
}

export interface UserResponse {
    id: number;
    fullName: string;
    username: string;
    about: string;
    avatar: Image;
    privateProfile: boolean;
    isUserBlocked: boolean;
    isMyProfileBlocked: boolean;
    isWaitingForApprove: boolean;
    isFollower: boolean;
}

export interface AuthUserResponse {
    id: number;
    email: string;
    fullName: string;
    username: string;
    location: string;
    about: string;
    website: string;
    countryCode: string;
    phone: number;
    country: string;
    gender: string;
    language: string;
    birthday: string;
    tweetCount: number;
    mediaTweetCount: number;
    likeCount: number;
    notificationsCount: number;
    active: boolean;
    profileCustomized: boolean;
    profileStarted: boolean;
    mutedDirectMessages: boolean;
    privateProfile: boolean;
    backgroundColor: string;
    colorScheme: string;
    avatar: Image;
    wallpaper: Image;
    pinnedTweetId: number;
    followersSize: number;
    followingSize: number;
    followerRequestsSize: number;
    unreadMessagesSize: number;
}

export interface BaseUserResponse {
    id: number;
    fullName: string;
    username: string;
    about: string;
    avatar: Image;
    isPrivateProfile: boolean;
    isUserBlocked: boolean;
    isMyProfileBlocked: boolean;
    isWaitingForApprove: boolean;
    isFollower: boolean;
    isUserMuted: boolean;
    isSubscriber: boolean;
}

export interface BlockedUserResponse {
    id: number;
    fullName: string;
    username: string;
    about: string;
    avatar: Image;
    isPrivateProfile: boolean;
    isUserBlocked: boolean;
}

export interface MutedUserResponse {
    id: number;
    fullName: string;
    username: string;
    about: string;
    avatar: Image;
    isPrivateProfile: boolean;
    isUserMuted: boolean;
}

export interface FollowerUserResponse {
    id: number;
    fullName: string;
    username: string;
    about: string;
    avatar: Image;
}

export interface UserDetailResponse {
    id: number;
    fullName: string;
    username: string;
    about: string;
    avatar: Image;
    isPrivateProfile: boolean;
    isUserBlocked: boolean;
    isMyProfileBlocked: boolean;
    isWaitingForApprove: boolean;
    isFollower: boolean;
    followersSize: number;
    followingSize: number;
    sameFollowers: SameFollowerResponse[];
}
