import React, {memo, ReactElement} from "react";
import {useSelector} from "react-redux";
import {Link, useLocation} from "react-router-dom";
import {Avatar} from "@material-ui/core";

import {PROFILE_PHOTO} from "../../../util/pathConstants";
import {DEFAULT_PROFILE_IMG} from "../../../util/url";
import {selectUserProfileAvatar, selectUserProfileId} from "../../../store/ducks/userProfile/selectors";

const UserAvatar = memo((): ReactElement => {
    const location = useLocation();
    const userProfileId = useSelector(selectUserProfileId);
    const avatar = useSelector(selectUserProfileAvatar);

    return (
        <Link to={{
            pathname: `${PROFILE_PHOTO}/${userProfileId}`,
            state: {background: location, imageSrc: avatar ? avatar.src : DEFAULT_PROFILE_IMG}
        }}>
            <div style={{display: "inline-block"}}>
                <Avatar src={userProfileId ? (avatar ? avatar.src : DEFAULT_PROFILE_IMG) : undefined}>
                    <div></div>
                </Avatar>
            </div>
        </Link>
    );
});

export default UserAvatar;
