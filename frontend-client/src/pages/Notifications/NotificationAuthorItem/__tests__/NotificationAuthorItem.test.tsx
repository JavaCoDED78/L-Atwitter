import React from "react";
import {Avatar} from "@material-ui/core";

import NotificationAuthorItem from "../NotificationAuthorItem";
import {createMockRootState, mockDispatch, mountWithStore} from "../../../../util/testHelper";
import {LoadingStatus} from "../../../../store/types";
import {mockTweetAuthors} from "../../../../util/mockData/mockData";
import PopperUserWindow from "../../../../components/PopperUserWindow/PopperUserWindow";
import {DEFAULT_PROFILE_IMG} from "../../../../util/url";
import {NotificationUserResponse} from "../../../../store/types/notification";

describe("NotificationAuthorItem", () => {
    const mockStore = createMockRootState(LoadingStatus.LOADED);
    const mockTweetAuthor = mockTweetAuthors[0];
    let mockDispatchFn: jest.Mock;

    beforeEach(() => {
        mockDispatchFn = mockDispatch();
    });

    it("should render correctly", () => {
        jest.useFakeTimers();
        const wrapper = mountWithStore(<NotificationAuthorItem tweetAuthor={mockTweetAuthor}/>, mockStore);

        expect(wrapper.find(Avatar).prop("src")).toBe(mockTweetAuthor.avatar.src);
        expect(wrapper.find(Avatar).prop("alt")).toBe(`avatar ${mockTweetAuthor.id}`);
        expect(wrapper.find(PopperUserWindow).prop("visible")).toBe(false);

        wrapper.find("div").at(0).simulate("mouseenter");
        jest.runAllTimers();
        wrapper.update();

        expect(wrapper.find(PopperUserWindow).prop("visible")).toBe(true);

        wrapper.find("div").at(0).simulate("mouseleave");
        expect(wrapper.find(PopperUserWindow).prop("visible")).toBe(false);

    });

    it("should render default avatar image", () => {
        const mockAuthor = {...mockTweetAuthor, avatar: {src: undefined}} as unknown as NotificationUserResponse;
        const wrapper = mountWithStore(<NotificationAuthorItem tweetAuthor={mockAuthor}/>, mockStore);

        expect(wrapper.find(Avatar).prop("src")).toBe(DEFAULT_PROFILE_IMG);

    });
});
