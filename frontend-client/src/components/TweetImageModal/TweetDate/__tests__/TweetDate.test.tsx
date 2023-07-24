import React from "react";
import format from "date-fns/format";
import usLang from "date-fns/locale/en-US/index";

import {createMockRootState, mountWithStore} from "../../../../util/testHelper";
import {LoadingStatus} from "../../../../store/types/common";
import {mockFullTweet} from "../../../../util/mockData/mockData";
import TweetDate from "../TweetDate";

describe("TweetDate", () => {
    it("should render correctly", () => {
        const wrapper = mountWithStore(<TweetDate/>, createMockRootState(LoadingStatus.SUCCESS));
        expect(wrapper.text().includes(`${format(new Date(mockFullTweet.dateTime), "hh:mm a", {locale: usLang})}`)).toBe(true);
        expect(wrapper.text().includes(`${format(new Date(mockFullTweet.dateTime), " MMM dd, yyyy")} · Twitter Web App`)).toBe(true);
    });
});
