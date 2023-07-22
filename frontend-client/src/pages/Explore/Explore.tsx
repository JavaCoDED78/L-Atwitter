import React, {ChangeEvent, FC, FormEvent, ReactElement, useEffect, useState} from 'react';
import {useHistory, useLocation} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import InfiniteScroll from "react-infinite-scroll-component";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import {IconButton, InputAdornment, List, Paper} from "@material-ui/core";

import {MainSearchTextField} from "../../components/SearchTextField/MainSearchTextField";
import {
    fetchMediaTweets,
    fetchTweets,
    fetchTweetsByTag,
    fetchTweetsByText,
    fetchTweetsWithVideo,
    resetTweets
} from "../../store/ducks/tweets/actionCreators";
import BackButton from "../../components/BackButton/BackButton";
import TweetComponent from "../../components/TweetComponent/TweetComponent";
import {
    selectIsTweetsLoaded,
    selectIsTweetsLoading,
    selectPagesCount,
    selectTweetsItems
} from "../../store/ducks/tweets/selectors";
import {useExploreStyles} from "./ExploreStyles";
import {EditIcon, SearchIcon} from "../../icons";
import {
    fetchUsersSearch,
    fetchUsersSearchByUsername,
    resetUsersState
} from "../../store/ducks/usersSearch/actionCreators";
import {
    selectUsersPagesCount,
    selectUsersSearch,
    selectUsersSearchIsLoading
} from "../../store/ducks/usersSearch/selectors";
import Spinner from "../../components/Spinner/Spinner";
import UsersItem, {UserItemSize} from "../../components/UsersItem/UsersItem";
import {useGlobalStyles} from "../../util/globalClasses";
import {withDocumentTitle} from "../../hoc/withDocumentTitle";
import PageHeaderWrapper from "../../components/PageHeaderWrapper/PageHeaderWrapper";

const Explore: FC = (): ReactElement => {
    const globalClasses = useGlobalStyles();
    const classes = useExploreStyles();
    const dispatch = useDispatch();
    const isTweetsLoading = useSelector(selectIsTweetsLoading);
    const isTweetsLoaded = useSelector(selectIsTweetsLoaded);
    const isUsersLoading = useSelector(selectUsersSearchIsLoading);
    const tweets = useSelector(selectTweetsItems);
    const users = useSelector(selectUsersSearch);
    const tweetsPagesCount = useSelector(selectPagesCount);
    const usersPagesCount = useSelector(selectUsersPagesCount);
    const location = useLocation<{ tag: string | undefined; text: string | undefined; }>();
    const history = useHistory();
    const [text, setText] = useState<string>("");
    const [activeTab, setActiveTab] = useState<number>(0);
    const [page, setPage] = useState<number>(0);

    useEffect(() => {
        window.scrollTo(0, 0);

        if (location.state?.tag) {
            dispatch(fetchTweetsByTag(location.state?.tag));
            setText(decodeURIComponent(location.state?.tag));
        }

        if (location.state?.text) {
            dispatch(fetchTweetsByText(location.state?.text));
            setText(decodeURIComponent(location.state?.text));
        }

        if (!location.state?.tag && !location.state?.text) {
            loadTweets();
        }

        return () => {
            dispatch(resetTweets());
        };
    }, [location.state?.tag, location.state?.text]);

    const loadTweets = (): void => {
        if (text) {
            if (activeTab !== 2) {
                dispatch(fetchTweetsByText(encodeURIComponent(text)));
            } else {
                dispatch(fetchUsersSearchByUsername({ username: encodeURIComponent(text), page }));
            }
        } else {
            if (activeTab === 2) {
                dispatch(fetchUsersSearch(page));
            } else if (activeTab === 3) {
                dispatch(fetchMediaTweets(page));
            } else if (activeTab === 4) {
                dispatch(fetchTweetsWithVideo(page));
            } else {
                dispatch(fetchTweets(page));
            }
        }

        if (isTweetsLoaded) {
            setPage(prevState => prevState + 1);
        }
    };

    const handleChangeTab = (event: ChangeEvent<{}>, newValue: number): void => {
        setText("");
        history.replace({pathname: location.pathname, state: {}});
        setActiveTab(newValue);
    };

    const handleSubmitSearch = (event: FormEvent<HTMLFormElement>): void => {
        event.preventDefault();

        if (text) {
            if (activeTab !== 2) {
                dispatch(resetTweets());
                dispatch(fetchTweetsByText(encodeURIComponent(text)));
            } else {
                dispatch(resetUsersState());
                dispatch(fetchUsersSearchByUsername({ username: encodeURIComponent(text), page: 0 }));
            }
        }
    };

    const handleShowItems = (callback: () => void): void => {
        window.scrollTo(0, 0);
        setPage(0);
        dispatch(resetTweets());
        dispatch(resetUsersState());
        callback();
    };

    const showTopTweets = (): void => {
        dispatch(fetchTweets(0));
        setPage(prevState => prevState + 1);
    };

    const showUsers = (): void => {
        dispatch(fetchUsersSearch(0));
        setPage(prevState => prevState + 1);
    };

    const showMediaTweets = (): void => {
        dispatch(fetchMediaTweets(0));
        setPage(prevState => prevState + 1);
    };

    const showTweetsWithVideos = (): void => {
        dispatch(fetchTweetsWithVideo(0));
        setPage(prevState => prevState + 1);
    };

    return (
        <InfiniteScroll
            style={{overflow: "unset"}}
            dataLength={tweets.length}
            next={loadTweets}
            hasMore={page < (activeTab === 2 ? usersPagesCount : tweetsPagesCount)}
            loader={null}
        >
            <Paper className={globalClasses.pageContainer} variant="outlined">
                <PageHeaderWrapper>
                    <div>
                        <form style={{display: "block"}} onSubmit={handleSubmitSearch}>
                            <div className={classes.backButtonWrapper}>
                                <BackButton/>
                            </div>
                            <MainSearchTextField
                                variant="outlined"
                                placeholder="Explore Twitter"
                                onChange={(event) => setText(event.target.value)}
                                value={text}
                                InputProps={{
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            {SearchIcon}
                                        </InputAdornment>
                                    ),
                                }}
                            />
                            <IconButton className={classes.editButton} color="primary" size="small">
                                <>{EditIcon}</>
                            </IconButton>
                        </form>
                        <div className={classes.tabs}>
                            <Tabs value={activeTab} indicatorColor="primary" textColor="primary" onChange={handleChangeTab}>
                                <Tab onClick={() => handleShowItems(showTopTweets)} label="Top"/>
                                <Tab onClick={() => handleShowItems(showTopTweets)} label="Latest"/>
                                <Tab onClick={() => handleShowItems(showUsers)} label="People"/>
                                <Tab onClick={() => handleShowItems(showMediaTweets)} label="Photos"/>
                                <Tab onClick={() => handleShowItems(showTweetsWithVideos)} label="Videos"/>
                            </Tabs>
                        </div>
                    </div>
                </PageHeaderWrapper>
                <div className={classes.contentWrapper}>
                    {(activeTab !== 2) ? (
                        <>
                            <>{tweets.map((tweet) => <TweetComponent key={tweet.id} item={tweet}/>)}</>
                            <>{isTweetsLoading && <Spinner/>}</>
                        </>
                    ) : (
                        <>
                            <List>
                                {users?.map((user) => (
                                    <UsersItem key={user.id} item={user} size={UserItemSize.MEDIUM}/>
                                ))}
                            </List>
                            {isUsersLoading && <Spinner/>}
                        </>
                    )}
                </div>
            </Paper>
        </InfiniteScroll>
    );
};

export default withDocumentTitle(Explore)("Explore");
