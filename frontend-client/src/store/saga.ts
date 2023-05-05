import { all } from "redux-saga/effects";
import { tweetsSaga } from "./actions/tweets/sagas";
import { tagsSaga } from "./actions/tags/sagas";
import { tweetSaga } from "./actions/tweet/sagas";
import { usersSaga } from "./actions/users/sagas";
import { userSaga } from "./actions/user/sagas";

export default function* rootSaga() {
  yield all([tweetsSaga(), tweetSaga(), tagsSaga(), usersSaga(), userSaga()]);
}
