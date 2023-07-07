import React, {FC, useState} from 'react';
import {useSelector} from "react-redux";
import {Link} from 'react-router-dom';
import Paper from '@material-ui/core/Paper';
import {Avatar, Button, Typography} from "@material-ui/core";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";

import {User} from "../../store/ducks/user/contracts/state";
import {selectUserData} from "../../store/ducks/user/selectors";
import {useFollowerStyles} from "./FollowerStyles";
import {DEFAULT_PROFILE_IMG} from "../../util/url";

interface FollowerProps {
    user: User;
    follow: (user: User) => void;
    unfollow: (user: User) => void;
}

const Follower: FC<FollowerProps> = ({user, follow, unfollow}) => {
    const classes = useFollowerStyles();
    const myProfile = useSelector(selectUserData);
    const [btnText, setBtnText] = useState<string>("Following");
    const [visibleUnfollowModal, setVisibleUnfollowModal] = useState<boolean>(false);
    const follower = myProfile?.user?.followers?.findIndex(follower => follower.id === user.id);

    const handleClickOpenUnfollowModal = (): void => {
        setVisibleUnfollowModal(true);
    };

    const onCloseUnfollowModal = (): void => {
        setVisibleUnfollowModal(false);
    };

    const handleFollow = (user: User): void => {
        follow(user);
    };

    const handleUnfollow = (user: User): void => {
        unfollow(user);
        setVisibleUnfollowModal(false);
    };

    return (
        <Paper className={classes.container} variant="outlined">
            <Link to={`/user/${user.id}`} className={classes.link}>
                <Avatar className={classes.linkAvatar} src={user?.avatar?.src ? user?.avatar.src : DEFAULT_PROFILE_IMG}/>
            </Link>
            <div style={{flex: 1}}>
                <div className={classes.header}>
                    <Link to={`/user/${user.id}`} className={classes.link}>
                        <div style={{width: 350}}>
                            <Typography className={classes.fullName}>
                                {user?.fullName}
                            </Typography>
                            <Typography className={classes.username} variant="caption" display="block" gutterBottom>
                                @{user?.username}
                            </Typography>
                        </div>
                    </Link>
                    <div>
                        {myProfile?.user.id !== user.id && (
                            follower === -1 ? (
                                <Button
                                    className={classes.outlinedButton}
                                    onClick={() => handleFollow(user)}
                                    color="primary"
                                    variant="outlined"
                                >
                                    Follow
                                </Button>
                            ) : (
                                <Button
                                    className={classes.containedButton}
                                    onMouseOver={() => setBtnText("Unfollow")}
                                    onMouseLeave={() => setBtnText("Following")}
                                    onClick={handleClickOpenUnfollowModal}
                                    color="primary"
                                    variant="contained"
                                >
                                    {btnText}
                                </Button>
                            )
                        )}
                    </div>
                </div>
                <Typography display="block">{user?.about}</Typography>
                <Dialog open={visibleUnfollowModal} onClose={onCloseUnfollowModal} aria-labelledby="form-dialog-title">
                    <DialogContent style={{padding: "0px 0px"}}>
                        <div className={classes.modalWrapper}>
                            <Typography className={classes.modalFullName}>
                                Unfollow {user?.fullName}?
                            </Typography>
                            <div className={classes.modalUsername}>
                                Their Tweets will no longer show up in your home timeline. You can still view their
                                profile, unless their Tweets are protected.
                            </div>
                            <div className={classes.modalButtonWrapper}>
                                <Button
                                    className={classes.modalCancelButton}
                                    onClick={onCloseUnfollowModal}
                                    variant="contained"
                                >
                                    Cancel
                                </Button>
                                <Button
                                    className={classes.modalUnfollowButton}
                                    onClick={() => handleUnfollow(user)}
                                    variant="contained"
                                    color="primary"
                                >
                                    Unfollow
                                </Button>
                            </div>
                        </div>
                    </DialogContent>
                </Dialog>
            </div>
        </Paper>
    );
};

export default Follower;
