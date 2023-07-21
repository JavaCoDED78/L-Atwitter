import React, {FC, ReactElement} from 'react';
import {Avatar, Typography} from "@material-ui/core";
import Paper from "@material-ui/core/Paper";
import {Link} from "react-router-dom";

import {useNotificationItemStyles} from "./NotificationItemStyles";
import {NotificationResponse} from "../../../../store/types/notification";
import {NotificationType} from '../../../../store/types/common';
import {LikeIcon, ListsIconFilled, ProfileIconFilled, RetweetIcon} from "../../../../icons";
import {DEFAULT_PROFILE_IMG} from "../../../../util/url";
import PopperUserWindow from "../../../../components/PopperUserWindow/PopperUserWindow";
import {textFormatter} from "../../../../util/textFormatter";
import {HoverUserProps, withHoverUser} from "../../../../hoc/withHoverUser";
import {LISTS, NOTIFICATION, PROFILE} from "../../../../util/pathConstants";

export interface NotificationItemProps {
    notification: NotificationResponse;
    handleClickUser: (userId: number, event: React.MouseEvent<HTMLAnchorElement>) => void;
}

const NotificationItem: FC<NotificationItemProps & HoverUserProps> = (
    {
        notification,
        handleClickUser,
        visiblePopperWindow,
        handleHoverPopper,
        handleLeavePopper
    }
): ReactElement => {
    const classes = useNotificationItemStyles();

    return (
        <Link to={(
            notification.notificationType === NotificationType.FOLLOW
        ) ? (
            `${PROFILE}/${notification.user.id}`
        ) : (
            (notification.notificationType === NotificationType.LISTS
            ) ? (
                `${LISTS}/${notification.list.id}`
            ) : (
                `${NOTIFICATION}/${notification.id}`
            )
        )}
        >
            <Paper className={classes.notificationWrapper} variant="outlined">
                <div className={classes.notificationIcon}>
                    {(notification.notificationType === NotificationType.LIKE) && (
                        <span id={"like"}>{LikeIcon}</span>
                    )}
                    {(notification.notificationType === NotificationType.RETWEET) && (
                        <span id={"retweet"}>{RetweetIcon}</span>
                    )}
                    {(notification.notificationType === NotificationType.FOLLOW) && (
                        <span id={"follow"}>{ProfileIconFilled}</span>
                    )}
                    {(notification.notificationType === NotificationType.LISTS) && (
                        <span id={"list"}>{ListsIconFilled}</span>
                    )}
                </div>
                <div style={{flex: 1}}>
                    <a
                        id={"clickUser"}
                        href={`${PROFILE}/${notification.user.id!}`}
                        onClick={event => handleClickUser(notification.user.id!, event)}
                        onMouseEnter={() => handleHoverPopper!(notification.user.id!)}
                        onMouseLeave={handleLeavePopper}
                    >
                        <Avatar
                            className={classes.notificationAvatar}
                            alt={`avatar ${notification.id}`}
                            src={(notification.user.avatar?.src) ? (
                                notification.user.avatar?.src
                            ) : (
                                DEFAULT_PROFILE_IMG
                            )}
                        />
                        <PopperUserWindow visible={visiblePopperWindow}/>
                    </a>
                    <div className={classes.notificationInfo}>
                        <Typography variant={"h6"} component={"span"}>
                            {`${notification.user.username} `}
                        </Typography>
                        <Typography variant={"body1"} component={"span"}>
                            {(notification.notificationType === NotificationType.FOLLOW) ? (
                                <>followed you</>
                            ) : (
                                (notification.notificationType === NotificationType.LISTS) ? (
                                    <>
                                        {"added you to their List "}
                                        <Typography variant={"h6"} component={"span"}>
                                            {notification.list.name}
                                        </Typography>
                                    </>
                                ) : (
                                    <>
                                        {(notification.notificationType === NotificationType.LIKE) ? (
                                            "liked"
                                        ) : (
                                            "Retweeted"
                                        )} your Tweet
                                    </>
                                )
                            )}
                        </Typography>
                    </div>
                    <Typography id={"asd"} variant={"body1"} component={"div"} className={classes.notificationText}>
                        {notification.tweet && textFormatter(notification.tweet.text)}
                    </Typography>
                </div>
            </Paper>
        </Link>
    );
};

export default withHoverUser(NotificationItem);
