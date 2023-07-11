import React, {FC, ReactElement, useEffect, useState} from 'react';
import {Link, useLocation, useParams} from 'react-router-dom';
import {useDispatch, useSelector} from 'react-redux';
import CircularProgress from '@material-ui/core/CircularProgress';
import Paper from '@material-ui/core/Paper';
import {Avatar, Divider, IconButton} from "@material-ui/core";
import Typography from '@material-ui/core/Typography';
import format from 'date-fns/format';
import usLang from 'date-fns/locale/en-US/index';
import SockJS from "sockjs-client";
import {CompatClient, Stomp} from "@stomp/stompjs";

import {selectIsTweetLoading, selectTweetData} from '../../store/ducks/tweet/selectors';
import {fetchTweetData, setTweetData} from '../../store/ducks/tweet/actionCreators';
import {fetchLikeTweet, fetchRetweet} from "../../store/ducks/tweets/actionCreators";
import {selectUserData} from "../../store/ducks/user/selectors";
import UsersListModal from "../../components/UsersListModal/UsersListModal";
import {AddTweetForm} from "../../components/AddTweetForm/AddTweetForm";
import TweetComponent from "../../components/TweetComponent/TweetComponent";
import {useFullTweetStyles} from "./FullTweetStyles";
import {DEFAULT_PROFILE_IMG, WS_URL} from "../../util/url";
import {
    FollowReplyIcon,
    LikeIcon,
    LikeOutlinedIcon,
    MentionReplyIcon,
    PinOutlinedIcon,
    ReplyIcon,
    RetweetIcon,
    RetweetOutlinedIcon,
    RetweetOutlinedIconSm,
} from "../../icons";
import {textFormatter} from "../../util/textFormatter";
import VoteComponent from "../../components/VoteComponent/VoteComponent";
import {ReplyType} from "../../store/ducks/tweets/contracts/state";
import ShareTweet from "../../components/ShareTweet/ShareTweet";
import TweetComponentActions from "../../components/TweetComponentActions/TweetComponentActions";
import Quote from "../../components/Quote/Quote";
import PopperUserWindow from "../../components/PopperUserWindow/PopperUserWindow";

let stompClient: CompatClient | null = null;

export const FullTweet: FC = (): ReactElement | null => {
    const dispatch = useDispatch();
    const location = useLocation();
    const tweetData = useSelector(selectTweetData);
    const myProfile = useSelector(selectUserData);
    const isLoading = useSelector(selectIsTweetLoading);
    const params = useParams<{ id: string }>();

    const [visibleModalWindow, setVisibleModalWindow] = useState<boolean>(false);
    const [modalWindowTitle, setModalWindowTitle] = useState<string>("");
    const [visiblePopperUserWindow, setVisiblePopperUserWindow] = useState<boolean>(false);
    const [delayHandler, setDelayHandler] = useState<any>(null);

    const isTweetLiked = tweetData?.likedTweets.find((like) => like.user.id === myProfile?.id);
    const isTweetRetweeted = tweetData?.retweets.find((retweet) => retweet.user.id === myProfile?.id);
    const isFollower = myProfile?.followers?.findIndex((follower) => follower.id === tweetData?.user.id);
    const image = tweetData?.images?.[0];
    const classes = useFullTweetStyles({isTweetRetweeted, isTweetLiked});

    useEffect(() => {
        window.scrollTo(0, 0);
        if (params.id) {
            dispatch(fetchTweetData(params.id));

            stompClient = Stomp.over(new SockJS(WS_URL));
            stompClient.connect({}, () => {
                stompClient?.subscribe("/topic/tweet/" + params.id, (response) => {
                    dispatch(setTweetData(JSON.parse(response.body)));
                });
            });
        }

        return () => {
            stompClient?.disconnect();
            dispatch(setTweetData(undefined));
        };
    }, [dispatch, params.id]);

    const handleLike = (): void => {
        dispatch(fetchLikeTweet(params.id));
    };

    const handleRetweet = (): void => {
        dispatch(fetchRetweet(params.id));
    };

    const onOpenLikesModalWindow = (): void => {
        setVisibleModalWindow(true);
        setModalWindowTitle("Liked by");
    };

    const onOpenRetweetsModalWindow = (): void => {
        setVisibleModalWindow(true);
        setModalWindowTitle("Retweeted by");
    };

    const onCloseModalWindow = (): void => {
        setVisibleModalWindow(false);
        setModalWindowTitle("");
    };

    const handleHover = (): void => {
        setDelayHandler(setTimeout(() => setVisiblePopperUserWindow(true), 1337));
    };

    const handleLeave = (): void => {
        clearTimeout(delayHandler);
        setVisiblePopperUserWindow(false);
    };

    if (tweetData) {
        return (
            <div style={{paddingTop: 48}}>
                {isTweetRetweeted && (
                    <div className={classes.retweetWrapper}>
                        <span>{RetweetOutlinedIconSm}</span>
                        <Typography>
                            You Retweeted
                        </Typography>
                    </div>
                )}
                {(myProfile?.pinnedTweet?.id === tweetData.id) && (
                    <div className={classes.retweetWrapper}>
                        <span>{PinOutlinedIcon}</span>
                        <Typography>
                            Pinned Tweet
                        </Typography>
                    </div>
                )}
                <Paper className={classes.container}>
                    {isLoading ? (
                        <div className={classes.loading}>
                            <CircularProgress/>
                        </div>
                    ) : (
                        <>
                            <div className={classes.header}>
                                <div className={classes.headerWrapper}>
                                    <Avatar
                                        className={classes.avatar}
                                        alt={`avatar ${tweetData.user.id}`}
                                        src={tweetData.user.avatar?.src ? tweetData.user.avatar?.src : DEFAULT_PROFILE_IMG}
                                    />
                                    <Typography
                                        style={{position: "relative"}}
                                        onMouseEnter={handleHover}
                                        onMouseLeave={handleLeave}
                                    >
                                        <Link to={`/user/${tweetData.user.id}`}>
                                            <b>{tweetData.user.fullName}</b>&nbsp;
                                        </Link>
                                        <div>
                                            <span className={classes.username}>@{tweetData.user.username}</span>&nbsp;
                                        </div>
                                        {visiblePopperUserWindow && <PopperUserWindow user={tweetData.user}/>}
                                    </Typography>
                                </div>
                                <TweetComponentActions tweet={tweetData} isFullTweet={true}/>
                            </div>
                            <Typography className={classes.textWrapper} gutterBottom>
                                {textFormatter(tweetData.text)}
                                {tweetData.quoteTweet && (
                                    <Quote
                                        quoteTweet={tweetData.quoteTweet}
                                        isTweetQuoted={true}
                                        isFullTweet={true}
                                    />
                                )}
                                {(tweetData.images?.length !== 0) &&
                                <Link to={{pathname: `/modal/${params.id}`, state: {background: location}}}>
                                    <div className={classes.image}>
                                        <img src={image?.src} alt={image?.src}/>
                                    </div>
                                </Link>
                                }
                                {tweetData.poll && <VoteComponent tweetId={tweetData.id} poll={tweetData.poll}/>}
                            </Typography>
                            <Typography style={{marginBottom: 16}}>
                                <span className={classes.date}>
                                    {format(new Date(tweetData.dateTime), 'hh:mm a', {locale: usLang})} ·
                                </span>
                                <span className={classes.date}>
                                    {format(new Date(tweetData.dateTime), ' MMM dd, yyyy')} · Twitter Web App
                                </span>
                            </Typography>
                            <Divider/>
                            {(tweetData.retweets.length !== 0 || tweetData.likedTweets.length !== 0) && (
                                <div className={classes.content}>
                                    {(tweetData.retweets.length !== 0) && (
                                        <a href={"javascript:void(0);"} onClick={onOpenRetweetsModalWindow}>
                                    <span style={{marginRight: 20}}>
                                        <b>{tweetData.retweets.length}</b>
                                        <span className={classes.contentItem}>
                                            Retweets
                                        </span>
                                    </span>
                                        </a>)
                                    }
                                    {(tweetData.likedTweets.length !== 0) && (
                                        <a href={"javascript:void(0);"} onClick={onOpenLikesModalWindow}>
                                    <span style={{marginRight: 20}}>
                                        <b>{tweetData.likedTweets.length}</b>
                                        <span className={classes.contentItem}>
                                            Likes
                                        </span>
                                    </span>
                                        </a>)
                                    }
                                </div>)
                            }
                            <div className={classes.info}>
                                <div className={classes.infoIcon}>
                                    <IconButton>
                                        <>{ReplyIcon}</>
                                    </IconButton>
                                </div>
                                <div className={classes.retweetIcon}>
                                    <IconButton onClick={handleRetweet}>
                                        {isTweetRetweeted ? (
                                            <>{RetweetIcon}</>
                                        ) : (
                                            <>{RetweetOutlinedIcon}</>
                                        )}
                                    </IconButton>
                                </div>
                                <div className={classes.likeIcon}>
                                    <IconButton onClick={handleLike}>
                                        {isTweetLiked ? (
                                            <>{LikeIcon}</>
                                        ) : (
                                            <>{LikeOutlinedIcon}</>
                                        )}
                                    </IconButton>
                                </div>
                                <ShareTweet
                                    tweetId={tweetData.id}
                                    isFullTweet={true}
                                />
                            </div>
                            <Divider/>
                            {(tweetData.replyType === ReplyType.FOLLOW || tweetData.replyType === ReplyType.MENTION) && (
                                <Paper variant="outlined" className={classes.replyInfoWrapper}>
                                    <div className={classes.replyInfo}>
                                        <div className={classes.iconWrapper}>
                                            <div className={classes.iconCircle}>
                                            <span className={classes.icon}>
                                                {(tweetData.replyType === ReplyType.FOLLOW) && (FollowReplyIcon)}
                                                {(tweetData.replyType === ReplyType.MENTION) && (MentionReplyIcon)}
                                            </span>
                                            </div>
                                        </div>
                                        <div className={classes.replyTextInfoWrapper}>
                                            <div className={classes.replyInfoTitle}>
                                                Who can reply?
                                            </div>
                                            <div className={classes.replyInfoText}>
                                                People @{tweetData.user.fullName}
                                                {(tweetData.replyType === ReplyType.FOLLOW) ? (" follows or ") : (" ")}
                                                mentioned can reply
                                            </div>
                                        </div>
                                    </div>
                                </Paper>
                            )}
                            {((tweetData.replyType !== ReplyType.FOLLOW) && (tweetData.replyType !== ReplyType.MENTION) ||
                                (myProfile?.id === tweetData?.user.id) || (isFollower && tweetData.replyType === ReplyType.FOLLOW)
                            ) ? (
                                <>
                                    <Typography className={classes.replyWrapper}>
                                        Replying to <Link to={`/user/${tweetData.user.id}`}>
                                        @{tweetData.user.username}
                                    </Link>
                                    </Typography>
                                    <AddTweetForm
                                        tweetId={tweetData.id}
                                        addressedUsername={tweetData.user.username}
                                        addressedId={tweetData.user.id}
                                        maxRows={15}
                                        title={"Tweet your reply"}
                                        buttonName={"Reply"}/>
                                </>
                            ) : null}
                            {(visibleModalWindow && modalWindowTitle === "Liked by") ? (
                                <UsersListModal
                                    users={tweetData.likedTweets}
                                    title={modalWindowTitle}
                                    visible={visibleModalWindow}
                                    onClose={onCloseModalWindow}/>
                            ) : (
                                <UsersListModal
                                    users={tweetData.retweets}
                                    title={modalWindowTitle}
                                    visible={visibleModalWindow}
                                    onClose={onCloseModalWindow}/>
                            )}
                        </>
                    )}
                </Paper>
                <div className={classes.divider}/>
                {tweetData.replies.map((tweet) => <TweetComponent key={tweet.id} images={tweet.images} {...tweet} />)}
            </div>
        );
    }
    return null;
};
