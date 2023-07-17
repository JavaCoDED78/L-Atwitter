import React, {FC, ReactElement, useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import {Avatar, Button, Typography} from "@material-ui/core";
import classNames from "classnames";

import {usePopperUserWindowStyles} from "./PopperUserWindowStyles";
import {DEFAULT_PROFILE_IMG} from "../../util/url";
import {User} from "../../store/ducks/user/contracts/state";
import {selectUserData} from "../../store/ducks/user/selectors";
import {addUserToBlocklist, followUser, unfollowUser} from "../../store/ducks/user/actionCreators";
import {followProfile, processFollowRequest, unfollowProfile} from "../../store/ducks/userProfile/actionCreators";
import {LockIcon} from "../../icons";
import FollowerGroup from "../FollowerGroup/FollowerGroup";
import {SnackbarProps, withSnackbar} from "../../hoc/withSnackbar";
import ActionSnackbar from "../ActionSnackbar/ActionSnackbar";

interface PopperUserWindowProps {
    visible?: boolean;
    isTweetComponent?: boolean;
    isTweetImageModal?: boolean;
    user: User;
}

const PopperUserWindow: FC<PopperUserWindowProps & SnackbarProps> = (
    {
        visible,
        user,
        isTweetComponent,
        isTweetImageModal,
        snackBarMessage,
        openSnackBar,
        setSnackBarMessage,
        setOpenSnackBar,
        onCloseSnackBar
    }
): ReactElement | null => {
    const classes = usePopperUserWindowStyles({isTweetComponent});
    const dispatch = useDispatch();
    const myProfile = useSelector(selectUserData);
    const [btnText, setBtnText] = useState<string>("Following");
    const [sameFollowers, setSameFollowers] = useState<User[]>([]);
    const [isUserBlocked, setIsUserBlocked] = useState<boolean>(false);
    const [isWaitingForApprove, setIsWaitingForApprove] = useState<boolean>(false);

    const isFollower = myProfile?.followers?.findIndex(follower => follower.id === user.id) !== -1;
    const isMyProfileBlocked = user?.userBlockedList?.findIndex(blockedUser => blockedUser.id === myProfile?.id) !== -1;

    useEffect(() => {
        if (visible) {
            const followers = myProfile?.followers?.filter(({id: id1}) => user?.followers?.some(({id: id2}) => id2 === id1));
            const userBlocked = myProfile?.userBlockedList?.findIndex(blockedUser => blockedUser.id === user?.id) !== -1;
            const waitingForApprove = user?.followerRequests?.findIndex(blockedUser => blockedUser.id === myProfile?.id) !== -1;
            setBtnText(waitingForApprove ? ("Pending") : (userBlocked ? "Blocked" : "Following"));
            setSameFollowers(followers!);
            setIsUserBlocked(userBlocked);
            setIsWaitingForApprove(waitingForApprove);
        }
    }, [visible, user, myProfile]);

    const handleProcessFollowRequest = (user: User): void => {
        dispatch(processFollowRequest(user.id!));
    };

    const handleFollow = (user: User): void => {
        if (user?.privateProfile) {
            handleProcessFollowRequest(user);
        } else {
            dispatch(followUser(user));
            dispatch(followProfile(user));
        }
    };

    const handleUnfollow = (user: User): void => {
        if (user?.privateProfile) {
            handleProcessFollowRequest(user);
        } else {
            dispatch(unfollowUser(user));
            dispatch(unfollowProfile(user));
        }
    };

    const onBlockUser = (): void => {
        dispatch(addUserToBlocklist(user?.id!));
        setBtnText(isUserBlocked ? "Following" : "Blocked");
        setSnackBarMessage!(`@${user?.username} has been ${isUserBlocked ? "unblocked" : "blocked"}.`);
        setOpenSnackBar!(true);
    };

    if (!visible) {
        return null;
    }

    return (
        <div
            className={classNames(
                classes.popperUserWindow,
                isTweetComponent && classes.tweetComponent,
                isTweetImageModal && classes.tweetImageModal
            )}
        >
            <div className={classes.headerWrapper}>
                <Link to={`/user/${user?.id}`}>
                    <Avatar
                        className={classes.avatar}
                        alt={`avatar ${user.id}`}
                        src={user.avatar?.src ? user.avatar?.src : DEFAULT_PROFILE_IMG}
                    />
                </Link>
                {(myProfile?.id === user.id) ? null : (
                    (isMyProfileBlocked) ? null : (
                        (!isFollower) ? (
                            (isUserBlocked) ? (
                                <Button
                                    onClick={onBlockUser}
                                    className={classNames(classes.primaryButton, classes.blockButton)}
                                    color="primary"
                                    variant="contained"
                                    onMouseOver={() => setBtnText("Unblock")}
                                    onMouseLeave={() => setBtnText("Blocked")}
                                >
                                    {btnText}
                                </Button>
                            ) : (
                                (isWaitingForApprove) ? (
                                    <Button
                                        onClick={() => handleProcessFollowRequest(user!)}
                                        className={classes.outlinedButton}
                                        color="primary"
                                        variant="outlined"
                                        onMouseOver={() => setBtnText("Cancel")}
                                        onMouseLeave={() => setBtnText("Pending")}
                                    >
                                        {btnText}
                                    </Button>
                                ) : (
                                    <Button
                                        className={classes.outlinedButton}
                                        onClick={() => handleFollow(user!)}
                                        color="primary"
                                        variant="outlined"
                                    >
                                        Follow
                                    </Button>
                                )
                            )
                        ) : (
                            <Button
                                className={classes.primaryButton}
                                onMouseOver={() => setBtnText("Unfollow")}
                                onMouseLeave={() => setBtnText("Following")}
                                onClick={() => handleUnfollow(user!)}
                                color="primary"
                                variant="contained"
                            >
                                {btnText}
                            </Button>
                        )
                    )
                )}
            </div>
            <div className={classes.userInfoWrapper}>
                <Link to={`/user/${user?.id}`}>
                    <div>
                        <Typography component={"span"} className={classes.fullName}>
                            {user.fullName}
                        </Typography>
                        {user?.privateProfile && (
                            <span className={classes.lockIcon}>
                                {LockIcon}
                            </span>
                        )}
                    </div>
                </Link>
                <Typography component={"div"} className={classes.username}>
                    @{user.username}
                </Typography>
            </div>
            {(isMyProfileBlocked) ? null : (
                <>
                    <Typography component={"div"} className={classes.userInfo}>
                        {user.about}
                    </Typography>
                    <div className={classes.userFollowersWrapper}>
                        <Link to={`/user/${user?.id}/following`} className={classes.followLink}>
                            <Typography component={"span"} className={classes.followerCount}>
                                {user?.followers?.length ? user?.followers?.length : 0}
                            </Typography>
                            <Typography component={"span"} className={classes.followerText}>
                                {"Following"}
                            </Typography>
                        </Link>
                        <Link to={`/user/${user?.id}/followers`} className={classes.followLink}>
                            <Typography component={"span"} className={classes.followerCount}>
                                {user?.following?.length ? user?.following?.length : 0}
                            </Typography>
                            <Typography component={"span"} className={classes.followerText}>
                                {"Followers"}
                            </Typography>
                        </Link>
                    </div>
                    {user.privateProfile ? null : (
                        <FollowerGroup user={user} sameFollowers={sameFollowers}/>
                    )}
                </>
            )}
            <ActionSnackbar
                snackBarMessage={snackBarMessage!}
                openSnackBar={openSnackBar!}
                onCloseSnackBar={onCloseSnackBar!}
            />
        </div>
    );
};

export default withSnackbar(PopperUserWindow);
