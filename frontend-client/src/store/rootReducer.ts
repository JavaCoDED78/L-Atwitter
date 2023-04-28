import {combineReducers} from "redux";
import {tweetsReducer} from "./actions/tweet/reducer";

export const rootReducer = combineReducers({
    tweets: tweetsReducer
});
