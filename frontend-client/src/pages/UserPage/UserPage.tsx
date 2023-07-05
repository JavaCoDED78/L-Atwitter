import React, {ChangeEvent, FC, useEffect, useState} from 'react';
import {RouteComponentProps} from 'react-router-dom';
import {useDispatch, useSelector} from "react-redux";
import classNames from "classnames";
import Paper from '@material-ui/core/Paper';
import {Avatar, Button, CircularProgress, Typography} from '@material-ui/core';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Skeleton from '@material-ui/lab/Skeleton';
import DateRangeIcon from '@material-ui/icons/DateRange';
import LocationOnOutlinedIcon from '@material-ui/icons/LocationOnOutlined';
import LinkOutlinedIcon from '@material-ui/icons/LinkOutlined';

import {useHomeStyles} from '../Home/HomeStyles';
import {BackButton} from "../../components/BackButton/BackButton";
import {selectIsTweetsLoading, selectTweetsItems} from "../../store/ducks/tweets/selectors";
import {fetchLikedTweets, fetchUserTweets, setTweetsLoadingStatus} from "../../store/ducks/tweets/actionCreators";
import Tweet from "../../components/Tweet/Tweet";
import EditProfileModal from "../../components/EditProfileModal/EditProfileModal";
import {fetchUserData} from "../../store/ducks/user/actionCreators";
import {LoadingStatus} from "../../store/types";
import {selectUserData} from "../../store/ducks/user/selectors";
import {selectUser} from "../../store/ducks/users/selectors";
import {fetchUser, fetchUsers, followUser, unfollowUser} from "../../store/ducks/users/actionCreators";
import {fetchTags} from "../../store/ducks/tags/actionCreators";
import "./UserPage.scss";

const UserPage: FC<RouteComponentProps<{ id: string }>> = ({match}) => {
    const classes = useHomeStyles();
    const dispatch = useDispatch();
    const tweets = useSelector(selectTweetsItems);
    const myProfile = useSelector(selectUserData);
    const userProfile = useSelector(selectUser);
    const isTweetsLoading = useSelector(selectIsTweetsLoading);

    const [activeTab, setActiveTab] = useState<number>(0);
    const [visibleEditProfile, setVisibleEditProfile] = useState<boolean>(false);
    const follower = userProfile?.following?.find((user) => user.id === myProfile?.user.id);

    useEffect(() => {
        dispatch(setTweetsLoadingStatus(LoadingStatus.LOADING));
        if (match.params.id) {
            dispatch(fetchUser(match.params.id));
        }
        dispatch(fetchUserData());
        dispatch(fetchUsers());
        dispatch(fetchTags());
    }, [match.params.id]);

    useEffect(() => {
        if (userProfile) {
            dispatch(fetchUserTweets(match.params.id));
        }
    }, [userProfile]);

    const handleChange = (event: ChangeEvent<{}>, newValue: number) => {
        setActiveTab(newValue);
    };

    const onOpenEditProfile = () => {
        setVisibleEditProfile(true);
    };

    const onCloseEditProfile = () => {
        setVisibleEditProfile(false);
    };

    const handleFollow = () => {
        if (follower) {
            dispatch(unfollowUser(match.params.id));
        } else {
            dispatch(followUser(match.params.id));
        }
    };

    const handleShowUserTweets = () => {
        dispatch(fetchUserTweets(match.params.id));
    };

    const handleShowLikedTweets = () => {
        dispatch(fetchLikedTweets(match.params.id));
    };

    return (
        <Paper className={classNames(classes.tweetsWrapper, 'user')} variant="outlined">
            <Paper className={classes.tweetsHeader} variant="outlined">
                <BackButton/>
                <div>
                    <Typography variant="h6">{userProfile?.fullName}</Typography>
                    <Typography variant="caption" display="block" gutterBottom>
                        {tweets.length} Tweets
                    </Typography>
                </div>
            </Paper>
            <div className="user__header">
                <img className="user__header__img" key={userProfile?.wallpaper?.src} src={userProfile?.wallpaper?.src}/>
            </div>
            <div className="user__info">
                <div style={{display: "inline-block"}}>
                    <Avatar src={userProfile?.avatar?.src ? userProfile?.avatar.src :
                        "https://abs.twimg.com/sticky/default_profile_images/default_profile_reasonably_small.png"}/>
                </div>
                {userProfile?.id === myProfile?.user.id ? (
                    <Button onClick={onOpenEditProfile} color="primary" className={classes.profileMenuEditButton}>Edit
                        profile</Button>
                ) : (
                    follower ? (
                        <Button onClick={handleFollow} color="primary"
                                className={classes.profileMenuEditButton}>Unfollow</Button>
                    ) : (
                        <Button onClick={handleFollow} color="primary"
                                className={classes.profileMenuEditButton}>Follow</Button>
                    )
                )}
                {!userProfile ? (
                    <Skeleton variant="text" width={250} height={30}/>
                ) : (
                    <h2 className="user__info-fullname">{userProfile.fullName}</h2>
                )}
                {!userProfile ? (
                    <Skeleton variant="text" width={60}/>
                ) : (
                    <span className="user__info-username">@{userProfile.username}</span>
                )}
                <p className="user__info-description">{userProfile?.about}</p>
                <ul className="user__info-details">
                    {userProfile?.location ?
                        <li>
                            <LocationOnOutlinedIcon className="user__info-icon"/>{userProfile?.location}
                        </li> : null}
                    {userProfile?.website ?
                        <li>
                            <LinkOutlinedIcon className="user__info-icon"/>
                            <a className="link" href={userProfile?.website}>{userProfile?.website}</a>
                        </li> : null}
                    {userProfile?.dateOfBirth ?
                        <li>
                            Дата рождения: {userProfile?.dateOfBirth}
                        </li> : null}
                    {userProfile?.registration ?
                        <li>
                            <DateRangeIcon className="user__info-icon"/> Joined: {userProfile?.registration}
                        </li> : null}
                    <li><DateRangeIcon className="user__info-icon"/> Joined: June 2021</li>
                </ul>
                <br/>
                <ul className="user__info-details">
                    <li><b>{userProfile?.followers?.length ? userProfile?.followers?.length : 0}</b> Following</li>
                    <li><b>{userProfile?.following?.length ? userProfile?.following?.length : 0}</b> Followers</li>
                </ul>
            </div>
            <Tabs value={activeTab} indicatorColor="primary" textColor="primary" onChange={handleChange}>
                <Tab onClick={handleShowUserTweets} label="Tweets"/>
                <Tab onClick={handleShowUserTweets} label="Tweets & replies"/>
                <Tab label="Media"/>
                <Tab onClick={handleShowLikedTweets} label="Likes"/>
            </Tabs>
            <div className="user__tweets">
                {isTweetsLoading ? (
                    <div className={classes.tweetsCentred}>
                        <CircularProgress/>
                    </div>
                ) : (
                    tweets.map((tweet) => (
                        <Tweet key={tweet.id} classes={classes} images={tweet.images} {...tweet} />
                    ))
                )}
            </div>
            {visibleEditProfile ? <EditProfileModal visible={visibleEditProfile} onClose={onCloseEditProfile}/> : null}
        </Paper>
    );
};

export default UserPage;
