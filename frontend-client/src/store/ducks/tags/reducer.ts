import produce, {Draft} from 'immer';

import {LoadingStatus} from '../../types';
import {TagsActions, TagsActionsType} from './actionCreators';
import {TagsState} from './contracts/state';

const initialTagsState: TagsState = {
    items: [],
    loadingState: LoadingStatus.NEVER,
};

export const tagsReducer = produce((draft: Draft<TagsState>, action: TagsActions) => {
    switch (action.type) {
        case TagsActionsType.SET_TAGS:
            draft.items = action.payload;
            draft.loadingState = LoadingStatus.LOADED;
            break;

        case TagsActionsType.FETCH_TAGS:
            draft.items = [];
            draft.loadingState = LoadingStatus.LOADING;
            break;

        case TagsActionsType.SET_LOADING_STATE:
            draft.loadingState = action.payload;
            break;

        default:
            break;
    }
}, initialTagsState);
