import {
    FetchSearchByTextActionInterface,
    ResetSearchResultActionInterface,
    SearchActionsType,
    SetSearchLoadingStateActionInterface,
    SetSearchResultActionInterface
} from "./contracts/actionTypes";
import { SearchState } from "./contracts/state";
import { LoadingStatus } from "../../../types/common";

export const setSearchResult = (payload: SearchState["searchResult"]): SetSearchResultActionInterface => ({
    type: SearchActionsType.SET_SEARCH_RESULT,
    payload
});

export const fetchSearchByText = (payload: string): FetchSearchByTextActionInterface => ({
    type: SearchActionsType.FETCH_SEARCH_BY_TEXT,
    payload
});

export const resetSearchResult = (): ResetSearchResultActionInterface => ({
    type: SearchActionsType.RESET_SEARCH_RESULT
});

export const setSearchLoadingState = (payload: LoadingStatus): SetSearchLoadingStateActionInterface => ({
    type: SearchActionsType.SET_SEARCH_LOADING_STATE,
    payload
});
