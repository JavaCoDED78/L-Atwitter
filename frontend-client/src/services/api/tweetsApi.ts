import {TweetsState} from "../../store/actions/tweet/contracts/state";
import axios from "axios";

export const TweetsApi = {
    fetchTweets(): Promise<TweetsState['items']> {
        return axios.get('/tweets').then(({ data }) => data);
    },
};
