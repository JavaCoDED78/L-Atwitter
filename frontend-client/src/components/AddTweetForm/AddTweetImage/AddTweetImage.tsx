import React, { FC, memo, ReactElement } from "react";
import { useLocation } from "react-router-dom";

import { MODAL } from "../../../constants/path-constants";
import ActionIconButton from "../../ActionIconButton/ActionIconButton";
import { CloseIcon } from "../../../icons";
import { ImageObj } from "../AddTweetForm";
import { useAddTweetImageStyles } from "./AddTweetImageStyles";
import TagPeople from "./TagPeople/TagPeople";
import AddDescription from "./AddDescription/AddDescription";
import { UserResponse } from "../../../types/user";

interface AddTweetImageProps {
    images: ImageObj[];
    removeImage: () => void;
    imageDescription: string;
    handleChangeDescription: (description: string) => void;
    selectedUsers: UserResponse[];
    handleDelete: (selectedUser: UserResponse) => void;
    handleListItemClick: (user: UserResponse) => void;
}

const AddTweetImage: FC<AddTweetImageProps> = memo((
    {
        images,
        removeImage,
        imageDescription,
        handleChangeDescription,
        selectedUsers,
        handleDelete,
        handleListItemClick
    }
): ReactElement | null => {
    const classes = useAddTweetImageStyles();
    const location = useLocation();

    if (images.length === 0) {
        return null;
    }

    return (
        <div className={(location.pathname.includes(MODAL)) ? classes.imageSmall : classes.image}>
            <img src={images[0].src} alt={images[0].src} />
            <div>
                <TagPeople
                    selectedUsers={selectedUsers}
                    handleDelete={handleDelete}
                    handleListItemClick={handleListItemClick}
                />
                <AddDescription
                    imageSrc={images[0].src}
                    imageDescription={imageDescription}
                    handleChangeDescription={handleChangeDescription}
                />
            </div>
            <div className={classes.imageRemove}>
                <ActionIconButton
                    actionText={"Remove"}
                    icon={CloseIcon}
                    onClick={removeImage}
                    size={"medium"}
                />
            </div>
        </div>
    );
});

export default AddTweetImage;
