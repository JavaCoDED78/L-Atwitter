import React, {FC, ReactElement, useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Link, useParams} from "react-router-dom";
import {Avatar, Button, Paper, Typography} from "@material-ui/core";

import {useFullListStyles} from "./FullListStyles";
import {selectIsListLoading, selectListItem} from "../../store/ducks/list/selectors";
import {fetchListById, resetListState} from "../../store/ducks/list/actionCreators";
import BackButton from "../../components/BackButton/BackButton";
import {DEFAULT_PROFILE_IMG} from "../../util/url";
import {selectUserData} from "../../store/ducks/user/selectors";
import TweetComponent from "../../components/TweetComponent/TweetComponent";
import EditListModal from "./EditListModal/EditListModal";
import MembersAndFollowersModal from "./EditListModal/MembersAndFollowersModal/MembersAndFollowersModal";
import {followList} from "../../store/ducks/lists/actionCreators";
import ShareActionsModal from "./ShareActionsModal/ShareActionsModal";
import TopTweetsActionsModal from "./TopTweetsActionsModal/TopTweetsActionsModal";
import Spinner from "../../components/Spinner/Spinner";
import {LockIcon} from "../../icons";
import {useGlobalStyles} from "../../util/globalClasses";

const FullList: FC = (): ReactElement => {
    const globalClasses = useGlobalStyles();
    const classes = useFullListStyles();
    const dispatch = useDispatch();
    const list = useSelector(selectListItem);
    const myProfile = useSelector(selectUserData);
    const isLoading = useSelector(selectIsListLoading);
    const params = useParams<{ listId: string }>();

    const [btnText, setBtnText] = useState<string>("Following");
    const [visibleEditListModal, setVisibleEditListModal] = useState<boolean>(false);
    const [visibleMembersAndFollowersModal, setVisibleMembersAndFollowersModal] = useState<boolean>(false);
    const [modalWindowTitle, setModalWindowTitle] = useState<string>("");

    const follower = list?.followers.find((follower) => follower.id === myProfile?.id);

    useEffect(() => {
        window.scrollTo(0, 0);
        dispatch(fetchListById(params.listId));

        return () => {
            dispatch(resetListState());
        };
    }, [params.listId]);

    // Follow | Unfollow
    const handleFollow = (): void => {
        dispatch(followList(list?.id!));
    };

    const onOpenEditListModal = (): void => {
        setVisibleEditListModal(true);
    };

    const onCloseCreateListModal = (): void => {
        setVisibleEditListModal(false);
    };

    const onOpenMembersModalWindow = (): void => {
        setVisibleMembersAndFollowersModal(true);
        setModalWindowTitle("List members");
    };

    const onOpenFollowersModalWindow = (): void => {
        setVisibleMembersAndFollowersModal(true);
        setModalWindowTitle("List followers");
    };

    const onCloseModalWindow = (): void => {
        setVisibleMembersAndFollowersModal(false);
        setModalWindowTitle("");
    };

    return (
        <Paper className={globalClasses.pageContainer} variant="outlined">
            <Paper className={globalClasses.pageHeader} variant="outlined">
                <BackButton/>
                <div>
                    <div>
                        <Typography variant={"h5"} component={"span"}>
                            {list?.name}
                        </Typography>
                        {list?.private && (
                            <span className={classes.lockIcon}>
                                {LockIcon}
                            </span>
                        )}
                    </div>
                    <Typography variant={"subtitle2"} component={"div"}>
                        @{list?.listOwner.username}
                    </Typography>
                </div>
                <div className={classes.iconGroup}>
                    <ShareActionsModal/>
                    <TopTweetsActionsModal/>
                </div>
            </Paper>
            {isLoading ? (
                <Spinner paddingTop={250}/>
            ) : (
                <>
                    <div className={globalClasses.contentWrapper}>
                        <div className={classes.wallpaper}>
                            <img
                                key={list?.wallpaper?.src ? list?.wallpaper?.src : list?.altWallpaper}
                                src={list?.wallpaper?.src ? list?.wallpaper?.src : list?.altWallpaper}
                                alt={list?.wallpaper?.src ? list?.wallpaper?.src : list?.altWallpaper}
                            />
                        </div>
                        <Paper className={classes.listInfo} variant="outlined">
                            <div>
                                <Typography variant={"h5"} component={"span"}>
                                    {list?.name}
                                </Typography>
                                {list?.private && (
                                    <span className={classes.lockIcon}>
                                        {LockIcon}
                                    </span>
                                )}
                            </div>
                            <Typography variant={"body1"} component={"div"}>
                                {list?.description}
                            </Typography>
                            <Link to={`/user/${list?.listOwner.id}`} className={classes.listOwnerLink}>
                                <div className={classes.listOwnerWrapper}>
                                    <Avatar
                                        className={classes.listOwnerAvatar}
                                        src={list?.listOwner.avatar?.src ? list?.listOwner.avatar?.src : DEFAULT_PROFILE_IMG}
                                    />
                                </div>
                                <Typography variant={"h6"} component={"span"}>
                                    {list?.listOwner.fullName}
                                </Typography>
                                <Typography variant={"subtitle1"} component={"span"}>
                                    @{list?.listOwner.username}
                                </Typography>
                            </Link>
                            <div>
                                <span onClick={onOpenMembersModalWindow} className={classes.listMembers}>
                                    <Typography variant={"h6"} component={"span"}>
                                        {list?.members.length}
                                    </Typography>
                                    <Typography variant={"subtitle1"} component={"span"}>
                                        {" Members"}
                                    </Typography>
                                </span>
                                <span onClick={onOpenFollowersModalWindow} className={classes.listMembers}>
                                    <Typography variant={"h6"} component={"span"}>
                                        {list?.followers.length}
                                    </Typography>
                                    <Typography variant={"subtitle1"} component={"span"}>
                                        {" Followers"}
                                    </Typography>
                                </span>
                            </div>
                            <div className={classes.buttonWrapper}>
                                {(myProfile?.id === list?.listOwner.id) ? (
                                    <Button
                                        className={classes.listOutlinedButton}
                                        onClick={onOpenEditListModal}
                                        variant="outlined"
                                        color="primary"
                                        size="small"
                                    >
                                        Edit List
                                    </Button>
                                ) : (follower ? (
                                    <Button
                                        className={classes.primaryButton}
                                        onMouseOver={() => setBtnText("Unfollow")}
                                        onMouseLeave={() => setBtnText("Following")}
                                        onClick={handleFollow}
                                        variant="contained"
                                        color="primary"
                                        size="small"
                                    >
                                        {btnText}
                                    </Button>
                                ) : (
                                    <Button
                                        className={classes.outlinedButton}
                                        onClick={handleFollow}
                                        variant="outlined"
                                        color="primary"
                                        size="small"
                                    >
                                        Follow
                                    </Button>
                                ))}
                            </div>
                        </Paper>
                        {(list?.tweets.length === 0) ? (
                            <div className={globalClasses.infoText}>
                                <Typography variant={"h4"} component={"div"}>
                                    There aren’t any Tweets in this List
                                </Typography>
                                <Typography variant={"subtitle1"} component={"div"}>
                                    When anyone in this List Tweets, they’ll show up here.
                                </Typography>
                            </div>
                        ) : (
                            <>
                                {list?.tweets.map((tweet) => <TweetComponent key={tweet.id} item={tweet}/>)}
                            </>
                        )}
                    </div>
                    <EditListModal visible={visibleEditListModal} onClose={onCloseCreateListModal}/>
                    <MembersAndFollowersModal
                        visible={visibleMembersAndFollowersModal}
                        title={modalWindowTitle}
                        onClose={onCloseModalWindow}
                    />
                </>
            )}
        </Paper>
    );
};

export default FullList;
