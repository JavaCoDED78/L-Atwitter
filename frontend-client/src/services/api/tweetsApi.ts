import { Tweet, TweetsState } from "../../store/actions/tweets/contracts/state";
import axios from "axios";

export const TweetsApi = {
  fetchTweets(): Promise<TweetsState["items"]> {
    return axios.get("/tweets?_sort=id&_order=desc").then(({ data }) => data);
  },
  fetchTweetData(id: string): Promise<Tweet[]> {
    return axios.get("/tweets?_id=" + id).then(({ data }) => data);
  },
  addTweet(payload: Tweet): Promise<Tweet> {
    return axios.post("/tweets", payload).then(({ data }) => data);
  },
};
