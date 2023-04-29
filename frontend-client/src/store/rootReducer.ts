import { combineReducers } from "redux";
import { tweetsReducer } from "./actions/tweets/reducer";
import { tagsReducer } from "./actions/tags/reducer";

export const rootReducer = combineReducers({
  tweets: tweetsReducer,
  tags: tagsReducer,
  tweet: tweetsReducer,
});
