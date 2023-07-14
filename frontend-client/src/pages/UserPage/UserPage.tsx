import React, {ChangeEvent, FC, ReactElement, useEffect, useState} from 'react';
import {Link, RouteComponentProps, useHistory, useLocation} from 'react-router-dom';
import {useDispatch, useSelector} from "react-redux";
import InfiniteScroll from "react-infinite-scroll-component";
import Paper from '@material-ui/core/Paper';
import {Avatar, Button, CircularProgress, IconButton, List, ListItem, Snackbar, Typography} from '@material-ui/core';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Skeleton from '@material-ui/lab/Skeleton';
import format from 'date-fns/format';
import {CompatClient, Stomp} from "@stomp/stompjs";
import SockJS from "sockjs-client";
import classNames from "classnames";

import {CalendarIcon, LinkIcon, LocationIcon, MessagesIcon} from "../../icons";
import {useUserPageStyles} from "./UserPageStyles";
import {BackButton} from "../../components/BackButton/BackButton";
import EditProfileModal from "../../components/EditProfileModal/EditProfileModal";
import {
    addUserToBlocklist,
    addUserToMuteList,
    fetchUserData,
    follow,
    unfollow
} from "../../store/ducks/user/actionCreators";
import {selectUserData} from "../../store/ducks/user/selectors";
import {fetchRelevantUsers} from "../../store/ducks/users/actionCreators";
import {fetchTags} from "../../store/ducks/tags/actionCreators";
import {
    selectIsUserTweetsLoaded,
    selectIsUserTweetsLoading,
    selectPagesCount,
    selectUserTweetsItems
} from "../../store/ducks/userTweets/selectors";
import {
    deleteUserTweet,
    fetchUserLikedTweets,
    fetchUserMediaTweets,
    fetchUserRetweetsAndReplies,
    fetchUserTweets,
    resetUserTweets,
    setAddedUserTweet,
    setUpdatedUserTweet
} from "../../store/ducks/userTweets/actionCreators";
import {selectUserProfile} from "../../store/ducks/userProfile/selectors";
import {
    fetchUserProfile,
    followUserProfile,
    resetUserProfile,
    unfollowUserProfile
} from "../../store/ducks/userProfile/actionCreators";
import UserPageTweets from "./UserPageTweets";
import {DEFAULT_PROFILE_IMG, WS_URL} from "../../util/url";
import SetupProfileModal from "../SetupProfileModal/SetupProfileModal";
import UserPageActions from "./UserPageActions/UserPageActions";
import {createChat} from "../../store/ducks/chats/actionCreators";
import BlockUserModal from "../../components/BlockUserModal/BlockUserModal";

let stompClient: CompatClient | null = null;

const UserPage: FC<RouteComponentProps<{ id: string }>> = ({match}): ReactElement => {
    const classes = useUserPageStyles();
    const dispatch = useDispatch();
    const tweets = useSelector(selectUserTweetsItems);
    const myProfile = useSelector(selectUserData);
    const userProfile = useSelector(selectUserProfile);
    const isTweetsLoading = useSelector(selectIsUserTweetsLoading);
    const isTweetsLoaded = useSelector(selectIsUserTweetsLoaded);
    const location = useLocation<{ isRegistered: boolean; }>();
    const history = useHistory();

    const [btnText, setBtnText] = useState<string>("");
    const [activeTab, setActiveTab] = useState<number>(0);
    const [visibleBlockUserModal, setVisibleBlockUserModal] = useState<boolean>(false);
    const [visibleEditProfile, setVisibleEditProfile] = useState<boolean>(false);
    const [visibleSetupProfile, setVisibleSetupProfile] = useState<boolean>(false);
    const [snackBarMessage, setSnackBarMessage] = useState<string>("");
    const [openSnackBar, setOpenSnackBar] = useState<boolean>(false);
    const pagesCount = useSelector(selectPagesCount);
    const [page, setPage] = useState<number>(0);

    const follower = myProfile?.followers?.find((user) => user.id === userProfile?.id);
    const isUserMuted = myProfile?.userMutedList?.findIndex(mutedUser => mutedUser.id === userProfile?.id) !== -1;
    const isUserBlocked = myProfile?.userBlockedList?.findIndex(blockedUser => blockedUser.id === userProfile?.id) !== -1;

    useEffect(() => {
        window.scrollTo(0, 0);

        if (match.params.id) {
            dispatch(fetchUserProfile(match.params.id));
        }
        dispatch(fetchUserData());
        dispatch(fetchRelevantUsers());
        dispatch(fetchTags());
        document.body.style.overflow = 'unset';

        stompClient = Stomp.over(new SockJS(WS_URL));
        stompClient.connect({}, () => {
            stompClient?.subscribe("/topic/user/add/tweet/" + match.params.id, (response) => {
                dispatch(setAddedUserTweet(JSON.parse(response.body)));
            });
            stompClient?.subscribe("/topic/user/update/tweet/" + match.params.id, (response) => {
                if (JSON.parse(response.body).tweetDeleted) {
                    dispatch(deleteUserTweet(JSON.parse(response.body)));
                } else {
                    dispatch(setUpdatedUserTweet(JSON.parse(response.body)));
                }
            });
        });

        return () => {
            dispatch(resetUserProfile());
            dispatch(resetUserTweets());
            stompClient?.disconnect();
        };
    }, [match.params.id]);

    useEffect(() => {
        setBtnText(isUserBlocked ? "Blocked" : "Following");

        if (userProfile) {
            dispatch(fetchUserTweets({userId: match.params.id, page: 0}));
            setPage(prevState => prevState + 1);
        }

        if (location.state?.isRegistered) {
            setVisibleSetupProfile(true);
        }

        return () => {
            dispatch(resetUserTweets());
        };
    }, [userProfile]);

    useEffect(() => {
        setBtnText(isUserBlocked ? "Blocked" : "Following");
    }, [myProfile]);

    const loadUserTweets = (): void => {
        if (activeTab === 1) {
            dispatch(fetchUserRetweetsAndReplies({userId: match.params.id, page: page}));
        } else if (activeTab === 2) {
            dispatch(fetchUserMediaTweets({userId: match.params.id, page: page}));
        } else if (activeTab === 3) {
            dispatch(fetchUserLikedTweets({userId: match.params.id, page: page}));
        } else {
            dispatch(fetchUserTweets({userId: match.params.id, page: page}));
        }

        if (isTweetsLoaded) {
            setPage(prevState => prevState + 1);
        }
    };

    const showTweetCount = (): string => {
        if (userProfile !== undefined) {
            if (activeTab === 2) {
                return `${userProfile.mediaTweetCount} ${(userProfile.mediaTweetCount === 1) ? "Photo & video" : "Photos & videos"}`;
            } else if (activeTab === 3) {
                return `${userProfile.likeCount} ${(userProfile.likeCount === 1) ? "Like" : "Likes"}`;
            } else {
                return `${userProfile.tweetCount} ${(userProfile.tweetCount === 1) ? "Tweet" : "Tweets"}`;
            }
        }
        return "";
    };

    const handleShowTweets = (callback: () => void): void => {
        window.scrollTo(0, 0);
        setPage(0);
        dispatch(resetUserTweets());
        callback();
    };

    const handleChange = (event: ChangeEvent<{}>, newValue: number): void => {
        setActiveTab(newValue);
    };

    const onOpenEditProfile = (): void => {
        setVisibleEditProfile(true);
    };

    const onCloseEditProfile = (): void => {
        setVisibleEditProfile(false);
    };

    const onOpenSetupProfile = (): void => {
        setVisibleSetupProfile(true);
    };

    const onCloseSetupProfile = (): void => {
        setVisibleSetupProfile(false);
    };

    const handleFollow = (): void => {
        if (follower) {
            dispatch(unfollowUserProfile(userProfile!));
            dispatch(unfollow(userProfile!));
        } else {
            dispatch(followUserProfile(userProfile!));
            dispatch(follow(userProfile!));
        }
    };

    const handleShowUserTweets = (): void => {
        dispatch(fetchUserTweets({userId: match.params.id, page: 0}));
        setPage(prevState => prevState + 1);
    };

    const handleShowUserRetweetsAndReplies = (): void => {
        dispatch(fetchUserRetweetsAndReplies({userId: match.params.id, page: 0}));
        setPage(prevState => prevState + 1);
    };

    const handleShowMediaTweets = (): void => {
        dispatch(fetchUserMediaTweets({userId: match.params.id, page: 0}));
        setPage(prevState => prevState + 1);
    };

    const handleShowLikedTweets = (): void => {
        dispatch(fetchUserLikedTweets({userId: match.params.id, page: 0}));
        setPage(prevState => prevState + 1);
    };

    const handleClickAddUserToChat = (): void => {
        dispatch(createChat(userProfile?.id!));
        history.push("/messages");
    };

    const onMuteUser = (): void => {
        dispatch(addUserToMuteList(userProfile?.id!));
        setSnackBarMessage(`@${userProfile?.username} has been ${isUserMuted ? "unmuted" : "muted"}.`);
        setOpenSnackBar(true);
    };

    const onBlockUser = (): void => {
        dispatch(addUserToBlocklist(userProfile?.id!));
        setVisibleBlockUserModal(false);
        setBtnText(isUserBlocked ? "Following" : "Blocked");
        setSnackBarMessage(`@${userProfile?.username} has been ${isUserBlocked ? "unblocked" : "blocked"}.`);
        setOpenSnackBar(true);
    };

    const onOpenBlockUserModal = (): void => {
        setVisibleBlockUserModal(true);
    };

    const onCloseBlockUserModal = (): void => {
        setVisibleBlockUserModal(false);
    };

    const onCloseSnackBar = (): void => {
        setOpenSnackBar(false);
    };

    return (
        <InfiniteScroll
            style={{overflow: "unset"}}
            dataLength={tweets.length}
            next={loadUserTweets}
            hasMore={page < pagesCount}
            loader={null}
        >
            <Paper className={classes.container} variant="outlined">
                <Paper className={classes.header} variant="outlined">
                    <BackButton/>
                    <div>
                        <Typography component={"div"} className={classes.headerFullName}>
                            {userProfile?.fullName}
                        </Typography>
                        <Typography component={"div"} className={classes.headerTweetCount}>
                            {showTweetCount()}
                        </Typography>
                    </div>
                </Paper>
                <div style={{paddingTop: 53}}>
                    <div className={classes.wallpaper}>
                        <img
                            key={userProfile?.wallpaper?.src}
                            src={userProfile?.wallpaper?.src}
                            alt={userProfile?.wallpaper?.src}
                        />
                    </div>
                    <div className={classes.info}>
                        <div style={{display: "inline-block"}}>
                            <Avatar src={userProfile?.avatar?.src ? userProfile?.avatar.src : DEFAULT_PROFILE_IMG}/>
                        </div>
                        {(userProfile !== undefined) && (
                            (userProfile?.id === myProfile?.id) ? (
                                <Button
                                    onClick={myProfile?.profileCustomized ? onOpenEditProfile : onOpenSetupProfile}
                                    color="primary"
                                    className={classes.editButton}
                                >
                                    {myProfile?.profileCustomized ? "Edit profile" : "Setup profile"}
                                </Button>
                            ) : (
                                <div className={classes.buttonWrapper}>
                                    <UserPageActions
                                        user={userProfile!}
                                        isUserMuted={isUserMuted}
                                        isUserBlocked={isUserBlocked}
                                        onMuteUser={onMuteUser}
                                        onOpenBlockUserModal={onOpenBlockUserModal}
                                    />
                                    {!isUserBlocked && (
                                        <IconButton
                                            className={classes.messageButton}
                                            onClick={handleClickAddUserToChat}
                                            color="primary"
                                        >
                                            {MessagesIcon}
                                        </IconButton>
                                    )}
                                    {isUserBlocked ? (
                                        <Button
                                            onClick={onOpenBlockUserModal}
                                            className={classNames(classes.primaryButton, classes.blockButton)}
                                            color="primary"
                                            variant="contained"
                                            onMouseOver={() => setBtnText("Unblock")}
                                            onMouseLeave={() => setBtnText("Blocked")}
                                        >
                                            {btnText}
                                        </Button>
                                    ) : (
                                        follower ? (
                                            <Button
                                                onClick={handleFollow}
                                                className={classes.primaryButton}
                                                color="primary"
                                                variant="contained"
                                                onMouseOver={() => setBtnText("Unfollow")}
                                                onMouseLeave={() => setBtnText("Following")}
                                            >
                                                {btnText}
                                            </Button>
                                        ) : (
                                            <Button
                                                onClick={handleFollow}
                                                className={classes.editButton}
                                                color="primary"
                                            >
                                                Follow
                                            </Button>
                                        )
                                    )}
                                </div>
                            )
                        )}
                        {!userProfile ? (
                            <Skeleton variant="text" width={250} height={30}/>
                        ) : (
                            <Typography className={classes.fullName}>
                                {userProfile.fullName}
                            </Typography>
                        )}
                        {!userProfile ? (
                            <Skeleton variant="text" width={60}/>
                        ) : (
                            <Typography className={classes.username}>
                                @{userProfile.username}
                            </Typography>
                        )}
                        <Typography className={classes.description}>
                            {userProfile?.about}
                        </Typography>
                        <div className={classes.infoList}>
                            {!userProfile && (
                                <div className={classes.skeletonDetails}>
                                    <Skeleton component={"span"} variant="text" width={80}/>
                                    <Skeleton component={"span"} variant="text" width={150}/>
                                    <Skeleton component={"span"} variant="text" width={150}/>
                                </div>
                            )}
                            <List>
                                {userProfile?.location && (
                                    <ListItem>
                                        <>{LocationIcon}</>
                                        <Typography component={"span"}>
                                            {userProfile?.location}
                                        </Typography>
                                    </ListItem>
                                )}
                                {userProfile?.website && (
                                    <ListItem>
                                        <>{LinkIcon}</>
                                        <a className="link" href={userProfile?.website}>
                                            {userProfile?.website}
                                        </a>
                                    </ListItem>
                                )}
                                {userProfile?.dateOfBirth && (
                                    <ListItem>
                                        <Typography component={"span"}>
                                            Date of Birth: {userProfile?.dateOfBirth}
                                        </Typography>
                                    </ListItem>
                                )}
                                {userProfile?.registrationDate && (
                                    <ListItem>
                                        <>{CalendarIcon}</>
                                        Joined: {format(new Date(userProfile?.registrationDate), 'MMMM yyyy')}
                                    </ListItem>
                                )}
                            </List>
                            {!userProfile ? (
                                <div className={classes.skeletonDetails}>
                                    <Skeleton component={"span"} variant="text" width={80}/>
                                    <Skeleton component={"span"} variant="text" width={80}/>
                                </div>
                            ) : (
                                <List className={classes.details}>
                                    <Link to={`/user/${userProfile?.id}/following`} className={classes.followLink}>
                                        <ListItem>
                                            <b>
                                                {(userProfile?.id === myProfile?.id) ? (
                                                    myProfile?.followers?.length ? myProfile?.followers?.length : 0
                                                ) : (
                                                    userProfile?.followers?.length ? userProfile?.followers?.length : 0
                                                )}
                                            </b>
                                            <Typography component={"span"}>
                                                {" Following"}
                                            </Typography>
                                        </ListItem>
                                    </Link>
                                    <Link to={`/user/${userProfile?.id}/followers`} className={classes.followLink}>
                                        <ListItem>
                                            <b>
                                                {(userProfile?.id === myProfile?.id) ? (
                                                    myProfile?.following?.length ? myProfile?.following?.length : 0
                                                ) : (
                                                    userProfile?.following?.length ? userProfile?.following?.length : 0
                                                )}
                                            </b>
                                            <Typography component={"span"}>
                                                {" Followers"}
                                            </Typography>
                                        </ListItem>
                                    </Link>
                                </List>
                            )}
                        </div>
                    </div>
                    <div className={classes.tabs}>
                        <Tabs value={activeTab} indicatorColor="primary" textColor="primary" onChange={handleChange}>
                            <Tab onClick={() => handleShowTweets(handleShowUserTweets)} label="Tweets"/>
                            <Tab onClick={() => handleShowTweets(handleShowUserRetweetsAndReplies)} label="Tweets & replies"/>
                            <Tab onClick={() => handleShowTweets(handleShowMediaTweets)} label="Media"/>
                            <Tab onClick={() => handleShowTweets(handleShowLikedTweets)} label="Likes"/>
                        </Tabs>
                    </div>
                    <div className={classes.tweets}>
                        {(userProfile === undefined) ? (
                            <div className={classes.tweetsCentred}>
                                <CircularProgress/>
                            </div>
                        ) : (
                            <UserPageTweets
                                tweets={tweets}
                                isTweetsLoading={isTweetsLoading}
                                activeTab={activeTab}
                                userProfileId={userProfile?.id}
                                myProfileId={myProfile?.id}
                                username={userProfile?.username}
                            />
                        )}
                    </div>
                </div>
                <EditProfileModal visible={visibleEditProfile} onClose={onCloseEditProfile}/>
                <SetupProfileModal visible={visibleSetupProfile} onClose={onCloseSetupProfile}/>
                <BlockUserModal
                    username={userProfile?.username!}
                    isUserBlocked={isUserBlocked}
                    visible={visibleBlockUserModal}
                    onClose={onCloseBlockUserModal}
                    onBlockUser={onBlockUser}
                />
                <Snackbar
                    className={classes.snackBar}
                    anchorOrigin={{horizontal: "center", vertical: "bottom"}}
                    open={openSnackBar}
                    message={snackBarMessage}
                    onClose={onCloseSnackBar}
                    autoHideDuration={3000}
                />
            </Paper>
        </InfiniteScroll>
    );
};

export default UserPage;
