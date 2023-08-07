import { Image, LinkCoverSize, ReplyType } from "./common";

export interface TweetResponse {
    id: number;
    text: string;
    dateTime: string;
    scheduledDate: string;
    addressedUsername: string;
    addressedId: number;
    addressedTweetId: number;
    replyType: ReplyType;
    link: string;
    linkTitle: string;
    linkDescription: string;
    linkCover: string;
    linkCoverSize: LinkCoverSize;
    user: UserTweetResponse;
    images: Image[];
    quoteTweet: QuoteTweetResponse;
    poll: PollResponse;
    retweetsCount: number;
    likedTweetsCount: number;
    repliesCount: number;
    quotesCount: number;
    isDeleted: boolean;
    isTweetLiked: boolean;
    isTweetRetweeted: boolean;
    isUserFollowByOtherUser: boolean;
    isTweetDeleted: boolean;
    isTweetBookmarked: boolean;
    retweetsUserIds?: number[];
}

export interface TweetAdditionalInfoResponse {
    text: string;
    replyType: ReplyType;
    addressedTweetId: number;
    isDeleted: boolean;
    user: {
        id: number;
        fullName: string;
        username: string;
        isFollower: boolean;
        isMyProfileBlocked: boolean;
        isUserBlocked: boolean;
        isUserMuted: boolean;
    };
}

export interface UserTweetResponse {
    id: number;
    email: string;
    fullName: string;
    username: string;
    avatar: string;
    isPrivateProfile: boolean;
    isFollower: boolean;
    isMyProfileBlocked: boolean;
    isUserBlocked: boolean;
    isUserMuted: boolean;
}

export interface QuoteTweetResponse {
    id: number;
    text: string;
    dateTime: string;
    link: string;
    linkTitle: string;
    linkDescription: string;
    linkCover: string;
    linkCoverSize: string;
    isDeleted: boolean;
    user: UserTweetResponse;
}

export interface PollResponse {
    id: number;
    dateTime: string;
    pollChoices: PollChoiceResponse[];
}

export interface PollChoiceResponse {
    id: number;
    choice: string;
    votedUser: VotedUserResponse[];
}

export interface VotedUserResponse {
    id: number;
}

export interface TweetImageResponse {
    tweetId: number;
    imageId: number;
    src: string;
}

interface TweetsActionsPayload {
    userId: number;
    tweetId: number;
}

export interface FollowToTweetsPayload extends TweetsActionsPayload {
    isFollower: boolean;
}

export interface BlockedToTweetsPayload extends TweetsActionsPayload {
    isUserBlocked: boolean;
}

export interface MutedToTweetsPayload extends TweetsActionsPayload {
    isUserMuted: boolean;
}
