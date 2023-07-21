import React from "react";

import Trends from "../Trends";
import {createMockRootState, mockDispatch, mountWithStore} from "../../../util/testHelper";
import {LoadingStatus} from "../../../store/types";
import Spinner from "../../../components/Spinner/Spinner";
import {TagsActionsType} from "../../../store/ducks/tags/contracts/actionTypes";
import {mockTags} from "../../../util/mockData/mockData";
import TrendsItem from "../TrendsItem/TrendsItem";

window.scrollTo = jest.fn();

describe("Trends", () => {
    const mockRootState = createMockRootState(LoadingStatus.LOADED);
    let mockDispatchFn: jest.Mock;

    beforeEach(() => {
        mockDispatchFn = mockDispatch();
    });

    it("should render loading spinner", () => {
        const wrapper = mountWithStore(<Trends/>, createMockRootState());
        expect(wrapper.find(Spinner).exists()).toBe(true);
        expect(mockDispatchFn).nthCalledWith(1, {type: TagsActionsType.FETCH_TRENDS});
    });

    it("should render correctly", () => {
        const wrapper = mountWithStore(<Trends/>, {...mockRootState, tags: {...mockRootState.tags, items: mockTags}});
        expect(wrapper.find(Spinner).exists()).toBe(false);
        expect(wrapper.find(TrendsItem).length).toEqual(3);
    });
});
