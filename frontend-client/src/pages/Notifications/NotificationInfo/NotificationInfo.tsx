import React, {FC, ReactElement, useEffect} from 'react';
import {useLocation} from "react-router-dom";
import {Typography} from "@material-ui/core";
import Paper from "@material-ui/core/Paper";

import BackButton from "../../../components/BackButton/BackButton";
import {Notification, NotificationType} from "../../../store/ducks/notifications/contracts/state";
import TweetComponent from "../../../components/TweetComponent/TweetComponent";
import UsersItem, {UserItemSize} from "../../../components/UsersItem/UsersItem";
import {useGlobalStyles} from "../../../util/globalClasses";

const NotificationInfo: FC = (): ReactElement => {
    const globalClasses = useGlobalStyles();
    const location = useLocation<{ notification: Notification; }>();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, []);

    return (
        <Paper className={globalClasses.pageContainer} variant="outlined">
            <Paper className={globalClasses.pageHeader} variant="outlined">
                <BackButton/>
                <div>
                    <Typography variant="h5" component={"div"}>
                        {location.state.notification.notificationType === NotificationType.LIKE ? "Liked" : "Retweeted"}
                    </Typography>
                    <Typography variant="caption" component={"div"}>
                        by {location.state.notification.user.fullName}
                    </Typography>
                </div>
            </Paper>
            <div className={globalClasses.contentWrapper}>
                <TweetComponent item={location.state.notification.tweet}/>
            </div>
            <UsersItem item={location.state.notification.user} size={UserItemSize.MEDIUM}/>
        </Paper>
    );
};

export default NotificationInfo;
