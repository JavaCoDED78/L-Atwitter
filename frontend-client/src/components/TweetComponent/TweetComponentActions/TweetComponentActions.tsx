import React, {FC, ReactElement, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {ClickAwayListener, IconButton, List, ListItem} from "@material-ui/core";

import {useTweetComponentMoreStyles} from "./TweetComponentActionsStyles";
import {
    AddListsIcon,
    BlockIcon,
    DeleteIcon,
    EditIcon,
    EmbedTweetIcon,
    FollowIcon,
    MuteIcon,
    PinIcon,
    ReplyIcon,
    ReportIcon,
    TweetActivityIcon,
    UnfollowIcon
} from "../../../icons";
import {selectUserData} from "../../../store/ducks/user/selectors";
import {Tweet} from "../../../store/ducks/tweets/contracts/state";
import {fetchPinTweet, fetchUnpinTweet, followUser, unfollowUser} from "../../../store/ducks/user/actionCreators";
import {pinTweet} from "../../../store/ducks/userTweets/actionCreators";
import TweetPinModal from "../TweetPinModal/TweetPinModal";
import {User} from "../../../store/ducks/user/contracts/state";

interface TweetMoreProps {
    tweet: Tweet;
    user: User;
    activeTab?: number;
}

const TweetComponentActions: FC<TweetMoreProps> = ({tweet, user, activeTab}): ReactElement => {
    const classes = useTweetComponentMoreStyles();
    const dispatch = useDispatch();
    const myProfile = useSelector(selectUserData);
    const [open, setOpen] = useState<boolean>(false);
    const [visibleTweetPinModal, setVisibleTweetPinModal] = useState<boolean>(false);
    const follower = myProfile?.followers?.find((follower) => follower.id === user.id);
    const isTweetPinned = myProfile?.pinnedTweet?.id === tweet.id;

    const handleClick = (): void => {
        setOpen((prev) => !prev);
    };

    const handleClickAway = (): void => {
        setOpen(false);
    };

    const onPinUserTweet = (): void => {
        if (isTweetPinned) {
            dispatch(fetchUnpinTweet(tweet.id));
        } else {
            dispatch(fetchPinTweet(tweet.id));
            dispatch(pinTweet({tweet, activeTab}));
        }
        setOpen(false);
        setVisibleTweetPinModal(false);
    };

    const handleFollow = (): void => {
        if (follower) {
            dispatch(unfollowUser(user!));
        } else {
            dispatch(followUser(user!));
        }
    };

    const onOpenTweetPinModal = (): void => {
        setVisibleTweetPinModal(true);
    };

    const onCloseTweetPinModal = (): void => {
        setVisibleTweetPinModal(false);
    };

    return (
        <div>
            <ClickAwayListener onClickAway={handleClickAway}>
                <div className={classes.root}>
                    <IconButton onClick={handleClick}>
                        <span>{EditIcon}</span>
                    </IconButton>
                    {open ? (
                        <div className={classes.dropdown}>
                            <List>
                                {(myProfile?.id === user.id) ? (
                                    <>
                                        <ListItem className={classes.delete}>
                                            <span>{DeleteIcon}</span>
                                            <span className={classes.text}>Delete</span>
                                        </ListItem>
                                        <ListItem onClick={onOpenTweetPinModal}>
                                            <span className={classes.textIcon}>{PinIcon}</span>
                                            <span className={classes.text}>
                                            {(isTweetPinned) ? (
                                                "Unpin from profile"
                                            ) : (
                                                "Pin to your profile"
                                            )}
                                        </span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{AddListsIcon}</span>
                                            <span
                                                className={classes.text}>{`Add/remove @${user.username} from Lists`}</span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{ReplyIcon}</span>
                                            <span className={classes.text}>Change who can reply</span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{EmbedTweetIcon}</span>
                                            <span className={classes.text}>Embed Tweet</span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{TweetActivityIcon}</span>
                                            <span className={classes.text}>View Tweet activity</span>
                                        </ListItem>
                                    </>
                                ) : (
                                    <>
                                        <ListItem onClick={handleFollow}>
                                            {follower ? (
                                                <>
                                                    <span className={classes.textIcon}>{UnfollowIcon}</span>
                                                    <span className={classes.text}>{`Unfollow @${user.username}`}</span>
                                                </>
                                            ) : (
                                                <>
                                                    <span className={classes.textIcon}>{FollowIcon}</span>
                                                    <span className={classes.text}>{`Follow @${user.username}`}</span>
                                                </>
                                            )}
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{AddListsIcon}</span>
                                            <span
                                                className={classes.text}>{`Add/remove @${user.username} from Lists`}</span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{MuteIcon}</span>
                                            <span className={classes.text}>{`Mute @${user.username}`}</span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{BlockIcon}</span>
                                            <span className={classes.text}>{`Block @${user.username}`}</span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{EmbedTweetIcon}</span>
                                            <span className={classes.text}>Embed Tweet</span>
                                        </ListItem>
                                        <ListItem>
                                            <span className={classes.textIcon}>{ReportIcon}</span>
                                            <span className={classes.text}>Report Tweet</span>
                                        </ListItem>
                                    </>
                                )}
                            </List>
                        </div>
                    ) : null}
                </div>
            </ClickAwayListener>
            <TweetPinModal
                isTweetPinned={isTweetPinned}
                visibleTweetPinModal={visibleTweetPinModal}
                onCloseTweetPinModal={onCloseTweetPinModal}
                onPinUserTweet={onPinUserTweet}/>
        </div>
    );
};

export default TweetComponentActions;
