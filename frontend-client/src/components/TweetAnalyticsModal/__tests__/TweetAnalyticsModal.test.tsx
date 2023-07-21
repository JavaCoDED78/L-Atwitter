import React from "react";
import {Dialog} from "@material-ui/core";

import {createMockRootState, mountWithStore} from "../../../util/testHelper";
import {LoadingStatus} from "../../../store/types";
import {mockFullTweet} from "../../../util/mockData/mockData";
import TweetAnalyticsModal from "../TweetAnalyticsModal";

describe("TweetAnalyticsModal", () => {
    const mockRootState = createMockRootState(LoadingStatus.SUCCESS);

    it("should render correctly", () => {
        const wrapper = mountWithStore(
            <TweetAnalyticsModal
                tweet={mockFullTweet}
                visible={true}
                onClose={jest.fn()}
            />, mockRootState);

        expect(wrapper.text().includes("Tweet Analytics")).toBe(true);
        expect(wrapper.text().includes(mockFullTweet.user.fullName)).toBe(true);
        expect(wrapper.text().includes(mockFullTweet.user.username)).toBe(true);
        expect(wrapper.text().includes("Impressions")).toBe(true);
        expect(wrapper.text().includes("Total engagements")).toBe(true);
        expect(wrapper.text().includes("Promote your Tweet")).toBe(true);
        expect(wrapper.text().includes("Get more impressions on this Tweet!")).toBe(true);
    });
    
    it("should render empty TweetAnalyticsModal", () => {
        const wrapper = mountWithStore(
            <TweetAnalyticsModal
                tweet={mockFullTweet}
                visible={false}
                onClose={jest.fn()}
            />, mockRootState);
        expect(wrapper.find(Dialog).exists()).toBeFalsy();
    });
});
