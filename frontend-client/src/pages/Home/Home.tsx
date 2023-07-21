import React, {FC, ReactElement, useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Route, useLocation} from "react-router-dom";
import InfiniteScroll from "react-infinite-scroll-component";
import {Divider, Paper, Typography} from "@material-ui/core";

import TweetComponent from "../../components/TweetComponent/TweetComponent";
import {useHomeStyles} from './HomeStyles';
import AddTweetForm from '../../components/AddTweetForm/AddTweetForm';
import {
    fetchFollowersTweets,
    fetchTweets,
    resetTweets,
    setTweetsLoadingState,
} from "../../store/ducks/tweets/actionCreators";
import {selectIsTweetsLoading, selectPagesCount, selectTweetsItems} from "../../store/ducks/tweets/selectors";
import BackButton from "../../components/BackButton/BackButton";
import {fetchUserData} from "../../store/ducks/user/actionCreators";
import Connect from "../Connect/Connect";
import Trends from "../Trends/Trends";
import {selectUserData} from "../../store/ducks/user/selectors";
import Welcome from "../../components/Welcome/Welcome";
import {LoadingStatus} from "../../store/types";
import FullTweet from "../FullTweet/FullTweet";
import Spinner from "../../components/Spinner/Spinner";
import {useGlobalStyles} from "../../util/globalClasses";
import classnames from "classnames";
import TopTweetActions from "./TopTweetActions/TopTweetActions";
import {withDocumentTitle} from "../../hoc/withDocumentTitle";
import {HOME, HOME_CONNECT, HOME_TRENDS, HOME_TWEET, SEARCH} from "../../util/pathConstants";

const Home: FC = (): ReactElement => {
    const globalClasses = useGlobalStyles();
    const classes = useHomeStyles();
    const dispatch = useDispatch();
    const location = useLocation<{ background: Location }>();
    const myProfile = useSelector(selectUserData);
    const tweets = useSelector(selectTweetsItems);
    const isLoading = useSelector(selectIsTweetsLoading);
    const pagesCount = useSelector(selectPagesCount);
    const [switchTweets, setSwitchTweets] = React.useState<boolean>(false);
    const [page, setPage] = React.useState<number>(0);

    useEffect(() => {
        dispatch(setTweetsLoadingState(LoadingStatus.NEVER));
        dispatch(fetchUserData());

        if (location.pathname !== SEARCH) {
            loadTweets();
        }
        document.body.style.overflow = 'unset';
        window.scrollTo(0, 0);

        return () => {
            dispatch(resetTweets());
        };
    }, []);
    
    const loadTweets = (): void => {
        if (switchTweets) {
            dispatch(fetchFollowersTweets(page));
        } else {
            dispatch(fetchTweets(page));
        }
        setPage(prevState => prevState + 1);
    };
    
    const handleLatestTweets = (): void => {
        dispatch(resetTweets());
        dispatch(fetchFollowersTweets(0));
        handleSwitchTweets(true);
    };
    
    const handleTopTweets = (): void => {
        dispatch(resetTweets());
        dispatch(fetchTweets(0));
        handleSwitchTweets(false);
    };

    const handleSwitchTweets = (condition: boolean): void => {
        setSwitchTweets(condition);
        setPage(prevState => prevState + 1);
    };

    return (
        <InfiniteScroll
            style={{overflow: "unset"}}
            dataLength={tweets.length}
            next={loadTweets}
            hasMore={page < pagesCount}
            loader={null}
        >
            <Paper className={globalClasses.pageContainer} variant="outlined">
                <Paper className={classnames(globalClasses.pageHeader, classes.header)} variant="outlined">
                    <Route path={HOME} exact>
                        <Typography variant="h5">
                            Home
                        </Typography>
                        <TopTweetActions
                            switchTweets={switchTweets}
                            handleLatestTweets={handleLatestTweets}
                            handleTopTweets={handleTopTweets}
                        />
                    </Route>
                    <Route path={HOME_TWEET}>
                        <div>
                            <BackButton/>
                            <Typography variant="h5">
                                Tweet
                            </Typography>
                        </div>
                    </Route>
                    <Route path={HOME_CONNECT}>
                        <div>
                            <BackButton/>
                            <Typography variant="h5">
                                Connect
                            </Typography>
                        </div>
                    </Route>
                    <Route path={HOME_TRENDS}>
                        <div>
                            <BackButton/>
                            <Typography variant="h5">
                                Trends
                            </Typography>
                        </div>
                    </Route>
                </Paper>

                <Route path={HOME} exact>
                    <div className={classes.addForm}>
                        <AddTweetForm title={"What's happening?"} buttonName={"Tweet"}/>
                    </div>
                    <Divider/>
                </Route>

                <Route path={HOME_CONNECT} exact>
                    <Connect/>
                </Route>

                <Route path={HOME_TRENDS} exact>
                    <Trends/>
                </Route>

                <Route path={HOME} exact>
                    {!myProfile?.profileStarted ? (
                        <Welcome/>
                    ) : (
                        <>
                            {tweets.map((tweet) => <TweetComponent key={tweet.id} item={tweet}/>)}
                            {isLoading && <Spinner/>}
                        </>
                    )}
                </Route>
                <Route path={HOME_TWEET + "/:id"} exact>
                    <FullTweet/>
                </Route>
            </Paper>
        </InfiniteScroll>
    );
};

export default withDocumentTitle(Home)("Home");
