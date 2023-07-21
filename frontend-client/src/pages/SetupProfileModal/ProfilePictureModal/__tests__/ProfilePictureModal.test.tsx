import React from "react";
import {Avatar, Button, Dialog} from "@material-ui/core";

import ProfilePictureModal from "../ProfilePictureModal";
import {createMockRootState, mountWithStore} from "../../../../util/testHelper";
import {LoadingStatus} from "../../../../store/types";
import {wallpapers} from "../../../../util/wallpapers";
import {DEFAULT_PROFILE_IMG} from "../../../../util/url";

describe("ProfilePictureModal", () => {
    const mockRootState = createMockRootState(LoadingStatus.LOADED);
    const mockImage = {
        src: wallpapers[0],
        file: new File([""], "test_filename", {type: "text/html"})
    };

    it("should render empty ProfilePictureModal", () => {
        const wrapper = mountWithStore(
            <ProfilePictureModal
                open={false}
                onClose={jest.fn()}
                avatar={mockImage}
                onChangeAvatar={jest.fn()}
                onOpenProfileHeaderModal={jest.fn()}
            />, mockRootState);

        expect(wrapper.find(Dialog).prop("open")).toBe(false);
    });

    it("should render correctly and empty avatar", () => {
        const wrapper = mountWithStore(
            <ProfilePictureModal
                open={true}
                onClose={jest.fn()}
                avatar={undefined}
                onChangeAvatar={jest.fn()}
                onOpenProfileHeaderModal={jest.fn()}
            />, mockRootState);

        expect(wrapper.find(Dialog).prop("open")).toBe(true);
        expect(wrapper.text().includes("Pick a profile picture")).toBe(true);
        expect(wrapper.text().includes("Have a favorite selfie? Upload it now.")).toBe(true);
        expect(wrapper.find(Avatar).prop("src")).toEqual(DEFAULT_PROFILE_IMG);
        expect(wrapper.find(Button).text().includes("Skip for now")).toBe(true);
    });

    it("should render correctly with avatar", () => {
        const mockOnOpenProfileHeaderModal = jest.fn();
        const wrapper = mountWithStore(
            <ProfilePictureModal
                open={true}
                onClose={jest.fn()}
                avatar={mockImage}
                onChangeAvatar={jest.fn()}
                onOpenProfileHeaderModal={mockOnOpenProfileHeaderModal}
            />, mockRootState);
        
        wrapper.find(Button).simulate("click");

        expect(mockOnOpenProfileHeaderModal).toHaveBeenCalled();
        expect(wrapper.find(Dialog).prop("open")).toBe(true);
        expect(wrapper.find(Avatar).prop("src")).toEqual(mockImage.src);
        expect(wrapper.find(Button).text().includes("Next")).toBe(true);
    });
});
