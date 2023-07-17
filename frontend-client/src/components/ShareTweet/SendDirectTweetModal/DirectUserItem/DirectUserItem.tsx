import React, {FC, ReactElement} from 'react';
import {Avatar, Typography} from "@material-ui/core";

import {useDirectUserItemStyles} from "./DirectUserItemStyles";
import {User} from "../../../../store/ducks/user/contracts/state";
import {DEFAULT_PROFILE_IMG} from "../../../../util/url";
import {CheckIcon, LockIcon} from "../../../../icons";

interface DirectUserItemProps {
    user: User;
    selected: boolean;
}

const DirectUserItem: FC<DirectUserItemProps> = ({user, selected}): ReactElement => {
    const classes = useDirectUserItemStyles();

    return (
        <div className={classes.container}>
            <Avatar
                className={classes.listAvatar}
                src={user?.avatar?.src ? user?.avatar.src : DEFAULT_PROFILE_IMG}
            />
            <div style={{flex: 1}}>
                <div className={classes.header}>
                    <div className={classes.headerInfo}>
                        <div>
                            <Typography component={"span"} className={classes.fullName}>
                                {user?.fullName}
                            </Typography>
                            {user?.privateProfile && (
                                <span className={classes.lockIcon}>
                                    {LockIcon}
                                </span>
                            )}
                        </div>
                        <Typography className={classes.username} variant="caption" display="block" gutterBottom>
                            @{user?.username}
                        </Typography>
                    </div>
                    {selected && <span className={classes.checkIcon}>{CheckIcon}</span>}
                </div>
            </div>
        </div>
    );
};

export default DirectUserItem;
