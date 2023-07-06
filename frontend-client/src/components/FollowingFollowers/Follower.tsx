import React, {FC, useState} from 'react';
import {useSelector} from "react-redux";
import {Link} from 'react-router-dom';
import Paper from '@material-ui/core/Paper';
import {Avatar, Button, Typography} from "@material-ui/core";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";

import {User} from "../../store/ducks/user/contracts/state";
import {selectUserData} from "../../store/ducks/user/selectors";
import {useHomeStyles} from "../../pages/Home/HomeStyles";

interface FollowerProps {
    classes: ReturnType<typeof useHomeStyles>;
    user: User;
    follow: (user: User) => void;
    unfollow: (user: User) => void;
}

const Follower: FC<FollowerProps> = ({user, classes, follow, unfollow}) => {
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
        <Paper className={classes.follower} variant="outlined">
            <Link to={`/user/${user.id}`} className={classes.followerLink}>
                <Avatar className={classes.followerAvatar} src={user?.avatar?.src ? user?.avatar.src :
                    "https://abs.twimg.com/sticky/default_profile_images/default_profile_reasonably_small.png"}/>
            </Link>
            <div style={{flex: 1}}>
                <div className={classes.followerWrapper}>
                    <Link to={`/user/${user.id}`} className={classes.followerLink}>
                        <div style={{width: 350}}>
                            <Typography className={classes.followerFullName}>
                                {user?.fullName}
                            </Typography>
                            <Typography className={classes.followerUsername} variant="caption" display="block"
                                        gutterBottom>
                                @{user?.username}
                            </Typography>
                        </div>
                    </Link>
                    <div>
                        {myProfile?.user.id === user.id ? null : (
                            follower === -1 ? (
                                <Button
                                    className={classes.followerOutlinedBtn}
                                    onClick={() => handleFollow(user)}
                                    color="primary"
                                    variant="outlined">
                                    Follow
                                </Button>
                            ) : (
                                <Button
                                    className={classes.followerBtn}
                                    onMouseOver={() => setBtnText("Unfollow")}
                                    onMouseLeave={() => setBtnText("Following")}
                                    onClick={handleClickOpenUnfollowModal}
                                    variant="contained"
                                    color="primary">
                                    {btnText}
                                </Button>
                            )
                        )}
                    </div>
                </div>
                <Typography display="block">{user?.about}</Typography>
                <Dialog open={visibleUnfollowModal} onClose={onCloseUnfollowModal} aria-labelledby="form-dialog-title">
                    <DialogContent style={{padding: "0px 0px"}}>
                        <div className={classes.followerModalContentWrapper}>
                            <Typography className={classes.followerModalFullName}>
                                Unfollow {user?.fullName}?
                            </Typography>
                            <div className={classes.followerModalUsername}>
                                Their Tweets will no longer show up in your home timeline. You can still view their
                                profile, unless their Tweets are protected.
                            </div>
                            <div className={classes.followerModalBtnContainer}>
                                <Button
                                    className={classes.followerModalCancelBtn}
                                    onClick={onCloseUnfollowModal}
                                    variant="contained">
                                    Cancel
                                </Button>
                                <Button
                                    className={classes.followerModalUnfollowBtn}
                                    onClick={() => handleUnfollow(user)}
                                    variant="contained"
                                    color="primary">
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
