import {LoadingStatus} from "../../../types";
import {User} from "../../user/contracts/state";

export interface Image {
    id: number;
    src: string;
}

export interface Tweet {
    id: string;
    text: string;
    addressedUsername: string;
    addressedId: number;
    addressedTweetId?: number;
    dateTime: string;
    replyType: ReplyType;
    images?: Image[];
    likedTweets: LikeTweet[];
    retweets: Retweet[];
    replies: Tweet[];
    quoteTweet?: Tweet;
    user: User;
    poll?: Poll;
    tweetDeleted?: boolean;
}

export interface LikeTweet {
    id: number;
    likeTweetDate: string;
    user: User;
    tweet: Tweet;
}

export interface Retweet {
    id: number;
    retweetDate: string;
    user: User;
    tweet: Tweet;
}

export interface Poll {
    id: number;
    dateTime: string;
    pollChoices: PollChoice[];
}

export interface PollChoice {
    id: number;
    choice: string;
    votedUser: User[];
}

export interface AddTweet {
    text: string;
    images: Image[];
    replyType: ReplyType;
    pollDateTime?: number;
    choices?: string[];
}

export interface AddQuoteTweet {
    text: string;
    images: Image[];
    replyType: ReplyType;
    tweetId: string;
}

export enum ReplyType {
    EVERYONE = "EVERYONE",
    FOLLOW = "FOLLOW",
    MENTION = "MENTION"
}

export interface Vote {
    tweetId: string;
    pollChoiceId: number;
}

export interface TweetsState {
    items: Tweet[];
    loadingState: LoadingStatus;
}
