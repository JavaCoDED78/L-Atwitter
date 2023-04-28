import {all} from "redux-saga/effects";
import {tweetsSaga} from "./actions/tweet/sagas";
import {tagsSaga} from "./actions/tags/sagas";

export default function* rootSaga() {
    yield all([tweetsSaga(), tagsSaga()])
}