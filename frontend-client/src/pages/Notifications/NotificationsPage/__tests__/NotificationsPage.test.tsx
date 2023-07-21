import React from "react";
import {createMemoryHistory} from "history";

import {createMockRootState, mockDispatch, mountWithStore} from "../../../../util/testHelper";
import {LoadingStatus} from "../../../../store/types";
import Spinner from "../../../../components/Spinner/Spinner";
import {NotificationsActionsType} from "../../../../store/ducks/notifications/contracts/actionTypes";
import {UserActionsType} from "../../../../store/ducks/user/contracts/actionTypes";
import {mockNotifications, mockTweetAuthors} from "../../../../util/mockData/mockData";
import NotificationAuthorItem from "../NotificationAuthorItem/NotificationAuthorItem";
import NotificationItem from "../NotificationItem/NotificationItem";
import {PROFILE} from "../../../../util/pathConstants";
import NotificationsPage from "../NotificationsPage";

window.scrollTo = jest.fn();

describe("NotificationsPage", () => {
    const mockStore = createMockRootState(LoadingStatus.LOADED);
    const mockNotificationsStore = {
        ...mockStore,
        notifications: {
            ...mockStore.notifications,
            notificationsList: mockNotifications,
            tweetAuthors: mockTweetAuthors,
        }
    };
    let mockDispatchFn: jest.Mock;

    beforeEach(() => {
        mockDispatchFn = mockDispatch();
    });

    it("should render loading Spinner", () => {
        const wrapper = mountWithStore(<NotificationsPage/>, createMockRootState());
        expect(wrapper.find(Spinner).exists()).toBe(true);
        expect(mockDispatchFn).nthCalledWith(1, {type: NotificationsActionsType.FETCH_NOTIFICATIONS});
        expect(mockDispatchFn).nthCalledWith(2, {type: UserActionsType.FETCH_USER_DATA});
    });

    it("should render empty All Notifications", () => {
        const wrapper = mountWithStore(<NotificationsPage/>, mockStore);
        expect(wrapper.text().includes("Nothing to see here — yet")).toBe(true);
        expect(wrapper.text().includes("From like to Retweets and whole lot more, this is where all the actions happens.")).toBe(true);
    });

    it("should render NotificationAuthorItem and NotificationItem", () => {
        const wrapper = mountWithStore(<NotificationsPage/>, mockNotificationsStore);
        expect(wrapper.find(NotificationItem).length).toEqual(3);
        expect(wrapper.find(NotificationAuthorItem).length).toEqual(2);
        expect(wrapper.text().includes(`New Tweet notifications for `)).toBe(true);
        expect(wrapper.text().includes(`${mockTweetAuthors[0].fullName} and ${mockTweetAuthors[1].fullName}`)).toBe(true);
    });

    it("should render more then 2 tweet authors", () => {
        const mockNotificationsStore = {
            ...mockStore,
            notifications: {
                ...mockStore.notifications,
                notificationsList: mockNotifications,
                tweetAuthors: [
                    ...mockTweetAuthors,
                    {
                        "id": 4,
                        "username": "JavaCat",
                        "fullName": "JavaCat",
                        "avatar": {
                            "id": 5,
                            "src": "https://twitterclonestorage.s3.eu-central-1.amazonaws.com/b999d944-c9ec-4a9c-b356-db937211df5c_Ec1OBK3XsAEjVZR.png"
                        },
                        "isFollower": false
                    },
                ]
            }
        };
        const wrapper = mountWithStore(<NotificationsPage/>, mockNotificationsStore);
        expect(wrapper.find(NotificationItem).length).toEqual(3);
        expect(wrapper.find(NotificationAuthorItem).length).toEqual(3);
        expect(wrapper.text().includes(`New Tweet notifications for `)).toBe(true);
        expect(wrapper.text().includes(`${mockTweetAuthors[0].fullName} and 2 others`)).toBe(true);
    });

    it("should click User NotificationItem", () => {
        const history = createMemoryHistory();
        const pushSpy = jest.spyOn(history, "push");
        const wrapper = mountWithStore(<NotificationsPage/>, mockNotificationsStore, history);
        wrapper.find(NotificationItem).at(0).find("#clickUser").simulate("click");
        expect(pushSpy).toHaveBeenCalled();
        expect(pushSpy).toHaveBeenCalledWith(`${PROFILE}/${mockNotifications[0].user.id}`);
    });

    it("should reset Notifications State", () => {
        const wrapper = mountWithStore(<NotificationsPage/>, mockNotificationsStore);
        wrapper.unmount();
        expect(mockDispatchFn).nthCalledWith(3, {type: NotificationsActionsType.RESET_NOTIFICATION_STATE});
    });
});
