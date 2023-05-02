import { applyMiddleware, compose, createStore } from "redux";
import { rootReducer } from "./rootReducer";
import { TweetsState } from "./actions/tweets/contracts/state";
import { TagsState } from "./actions/tags/contracts/state";
import createSagaMiddleware from "redux-saga";
import rootSaga from "./saga";
import { TweetState } from "./actions/tweet/contracts/state";

export interface RootState {
  tweets: TweetsState;
  tweet: TweetState;
  tags: TagsState;
}

const composeEnhancers =
  (typeof window !== "undefined" &&
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__) ||
  compose;

const sagaMiddleware = createSagaMiddleware();

export const store = createStore(
  rootReducer,
  composeEnhancers(applyMiddleware(sagaMiddleware))
);

sagaMiddleware.run(rootSaga);
