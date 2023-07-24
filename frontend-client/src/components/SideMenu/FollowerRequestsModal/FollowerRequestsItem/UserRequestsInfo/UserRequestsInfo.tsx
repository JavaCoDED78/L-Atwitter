import React, {FC, memo, ReactElement} from "react";
import {Typography} from "@material-ui/core";

import PopperUserWindow from "../../../../PopperUserWindow/PopperUserWindow";
import {useHoverUser} from "../../../../../hook/useHoverUser";
import {useFollowerRequestsItemStyles} from "../FollowerRequestsItemStyles";
import {FollowerUserResponse} from "../../../../../store/types/user";

interface UserRequestsInfoProps {
    user: FollowerUserResponse;
}

const UserRequestsInfo: FC<UserRequestsInfoProps> = memo(({user}): ReactElement => {
    const classes = useFollowerRequestsItemStyles();
    const {visiblePopperWindow, handleHoverPopper, handleLeavePopper} = useHoverUser();

    return (
        <div className={classes.header}>
            <div id={"handleLeavePopper"} onMouseLeave={handleLeavePopper} className={classes.headerUserInfo}>
                <Typography id={"handleHoverPopper"} variant={"h6"} onMouseEnter={() => handleHoverPopper(user.id!)}>
                    {user?.fullName}
                </Typography>
                <Typography variant={"subtitle1"}>
                    @{user?.username}
                </Typography>
                <Typography variant={"body1"}>
                    {user?.about}
                </Typography>
                <PopperUserWindow visible={visiblePopperWindow}/>
            </div>
        </div>
    );
});

export default UserRequestsInfo;
