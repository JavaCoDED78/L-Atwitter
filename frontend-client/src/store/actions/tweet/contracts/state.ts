import { Tweet } from "../../tweets/contracts/state";
import { LoadingStatus } from "../../../types";

export interface TweetState {
  data?: Tweet;
  loadingState: LoadingStatus;
}
