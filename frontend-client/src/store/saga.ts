import { all } from "redux-saga/effects";
import { tweetsSaga } from "./actions/tweets/sagas";
import { tagsSaga } from "./actions/tags/sagas";

export default function* rootSaga() {
  yield all([tweetsSaga(), tweetsSaga(), tagsSaga()]);
}
