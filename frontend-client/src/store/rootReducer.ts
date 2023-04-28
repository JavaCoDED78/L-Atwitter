import {combineReducers} from "redux";
import {tweetsReducer} from "./actions/tweet/reducer";
import {tagsReducer} from "./actions/tags/reducer";

export const rootReducer = combineReducers({
    tweets: tweetsReducer,
    tags: tagsReducer
});
