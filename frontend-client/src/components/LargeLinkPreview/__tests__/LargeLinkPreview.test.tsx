import React from "react";

import {createMockRootState, mountWithStore} from "../../../util/testHelper";
import {LoadingStatus} from "../../../store/types";
import LargeLinkPreview from "../LargeLinkPreview";
import {mockFullTweet} from "../../../util/mockData/mockData";

describe("LargeLinkPreview", () => {
    const mockRootState = createMockRootState(LoadingStatus.LOADED);

    it("should render correctly", () => {
        const wrapper = mountWithStore(<LargeLinkPreview tweet={mockFullTweet} isFullTweet={true} />, mockRootState);

        expect(wrapper.text().includes(mockFullTweet.linkTitle)).toBe(true);
        expect(wrapper.text().includes("www.youtube.com")).toBe(true);
    });
});
