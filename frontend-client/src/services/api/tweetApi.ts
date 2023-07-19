import {AxiosResponse} from "axios";

import {axios} from "../../core/axios";
import {AddQuoteTweet, AddTweet, ReplyType, Tweet, Vote} from "../../store/ducks/tweets/contracts/state";
import {API_URL} from "../../util/url";
import {ReplyTweet} from "../../store/ducks/tweet/contracts/state";
import {TweetResponse} from "../../store/types/tweet";
import {NotificationTweetResponse} from "../../store/types/notification";

interface Response<T> {
    status: string;
    data: T;
}

export const TweetApi = {
    async fetchTweets(payload: number): Promise<AxiosResponse<TweetResponse[]>> { // +
        return await axios.get<TweetResponse[]>(`${API_URL}/tweets`, {params: {page: payload}});
    },
    async fetchMediaTweets(payload: number): Promise<AxiosResponse<TweetResponse[]>> { // +
        return await axios.get<TweetResponse[]>(`${API_URL}/tweets/media`, {params: {page: payload}});
    },
    async fetchTweetsWithVideo(payload: number): Promise<AxiosResponse<TweetResponse[]>> { // +
        return await axios.get<TweetResponse[]>(`${API_URL}/tweets/video`, {params: {page: payload}});
    },
    async fetchScheduledTweets(): Promise<TweetResponse[]> { // +
        const {data} = await axios.get<TweetResponse[]>(`${API_URL}/tweets/schedule`);
        return data;
    },
    async fetchTweetData(id: number): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.get<Response<TweetResponse>>(`${API_URL}/tweets/${id}`);
        return data;
    },
    async createTweet(payload: AddTweet): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.post<Response<TweetResponse>>(`${API_URL}/tweets`, payload);
        return data;
    },
    async createPoll(payload: AddTweet): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.post<Response<TweetResponse>>(`${API_URL}/tweets/poll`, payload);
        return data;
    },
    async createScheduledTweet(payload: AddTweet): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.post<Response<TweetResponse>>(`${API_URL}/tweets/schedule`, payload);
        return data;
    },
    async updateScheduledTweet(payload: AddTweet): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.put<Response<TweetResponse>>(`${API_URL}/tweets/schedule`, payload);
        return data;
    },
    async deleteScheduledTweets(payload: { tweetsIds: number[] }): Promise<Response<string>> { // +
        const {data} = await axios.delete<Response<string>>(`${API_URL}/tweets/schedule`, {data: payload});
        return data;
    },
    async deleteTweet(tweetId: number): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.delete<Response<TweetResponse>>(`${API_URL}/tweets/${tweetId}`);
        return data;
    },
    async searchTweets(text: string): Promise<Response<TweetResponse[]>> { // +
        const {data} = await axios.get<Response<TweetResponse[]>>(`${API_URL}/tweets/search/${text}`);
        return data;
    },
    async likeTweet(id: number): Promise<Response<NotificationTweetResponse>> {
        const {data} = await axios.get<Response<NotificationTweetResponse>>(`${API_URL}/tweets/like/${id}`);
        return data;
    },
    async retweet(id: number): Promise<Response<NotificationTweetResponse>> {
        const {data} = await axios.get<Response<NotificationTweetResponse>>(`${API_URL}/tweets/retweet/${id}`);
        return data;
    },
    async replyTweet(payload: ReplyTweet): Promise<Response<TweetResponse>> {
        const {data} = await axios.post<Response<TweetResponse>>(`${API_URL}/tweets/reply/${payload.tweetId}`, payload);
        return data;
    },
    async quoteTweet(payload: AddQuoteTweet): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.post<Response<TweetResponse>>(`${API_URL}/tweets/quote/${payload.tweetId}`, payload);
        return data;
    },
    async changeTweetReplyType(payload: { tweetId: string; replyType: ReplyType; }): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.get<Response<TweetResponse>>(`${API_URL}/tweets/reply/change/${payload.tweetId}`,
            {params: {replyType: payload.replyType}});
        return data;
    },
    async voteInPoll(payload: Vote): Promise<Response<TweetResponse>> { // +
        const {data} = await axios.post<Response<TweetResponse>>(`${API_URL}/tweets/vote`, payload);
        return data;
    },
};
