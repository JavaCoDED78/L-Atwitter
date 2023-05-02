import { combineReducers } from "redux";
import { tweetsReducer } from "./actions/tweets/reducer";
import { tagsReducer } from "./actions/tags/reducer";
import { tweetReducer } from "./actions/tweet/reducer";

export const rootReducer = combineReducers({
  tweets: tweetsReducer,
  tweet: tweetReducer,
  tags: tagsReducer,
});
