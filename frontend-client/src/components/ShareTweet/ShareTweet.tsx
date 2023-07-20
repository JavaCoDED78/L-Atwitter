import React, {FC, ReactElement, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useLocation} from "react-router-dom";
import CopyToClipboard from 'react-copy-to-clipboard';
import {ClickAwayListener, IconButton, List, ListItem, Typography} from "@material-ui/core";
import classnames from "classnames";

import {useShareTweetModalStyles} from "./ShareTweetStyles";
import {AddBookmarksIcon, LinkIcon, MessagesIcon, ShareIcon} from "../../icons";
import {selectUserData} from "../../store/ducks/user/selectors";
import {removeTweetFromBookmarks} from "../../store/ducks/tweets/actionCreators";
import {CLIENT_URL} from "../../util/url";
import HoverAction from "../HoverAction/HoverAction";
import SendDirectTweetModal from "./SendDirectTweetModal/SendDirectTweetModal";
import ActionSnackbar from "../ActionSnackbar/ActionSnackbar";
import {SnackbarProps, withSnackbar} from "../../hoc/withSnackbar";
import {HoverActions} from "../../hoc/withHoverAction";
import {useGlobalStyles} from "../../util/globalClasses";
import {addTweetToBookmarks} from "../../store/ducks/tweet/actionCreators";
import {TweetResponse} from "../../store/types/tweet";

interface ShareTweetProps {
    tweet: TweetResponse;
    isFullTweet: boolean;
    visibleShareAction?: boolean;
    handleHoverAction?: (action: HoverActions) => void;
    handleLeaveAction?: () => void;
}

const ShareTweet: FC<ShareTweetProps & SnackbarProps> = (
    {
        tweet,
        isFullTweet,
        visibleShareAction,
        handleHoverAction,
        handleLeaveAction,
        snackBarMessage,
        openSnackBar,
        setSnackBarMessage,
        setOpenSnackBar,
        onCloseSnackBar
    }
): ReactElement => {
    const globalClasses = useGlobalStyles();
    const classes = useShareTweetModalStyles({isFullTweet});
    const dispatch = useDispatch();
    const location = useLocation();
    const myProfile = useSelector(selectUserData);
    const [open, setOpen] = useState<boolean>(false);
    const [visibleSendDirectTweetModal, setVisibleSendDirectTweetModal] = useState<boolean>(false);

    const handleClick = (): void => {
        setOpen((prev) => !prev);
    };

    const handleClickAway = (): void => {
        setOpen(false);
    };

    const onClickSendViaDirectMessage = (): void => {
        setVisibleSendDirectTweetModal(true);
    };

    const onCloseSendViaDirectMessage = (): void => {
        setVisibleSendDirectTweetModal(false);
    };

    const onClickAddTweetToBookmarks = (): void => {
        dispatch(addTweetToBookmarks(tweet.id));

        if (location.pathname.includes("/bookmarks")) {
            dispatch(removeTweetFromBookmarks(tweet.id));
        }
        setOpenSnackBar!(true);
        setSnackBarMessage!(tweet.isTweetBookmarked ? "Tweet removed to your Bookmarks" : "Tweet added to your Bookmarks");
        setOpen(false);
    };

    const onCopyLinkToTweet = (): void => {
        setOpenSnackBar!(true);
        setSnackBarMessage!("Copied to clipboard");
        setOpen(false);
    };

    const onSendDirectTweet = (): void => {
        setOpenSnackBar!(true);
        setSnackBarMessage!("Your Tweet was sent");
        setOpen(false);
    };

    const closeShareTweet = (): void => {
        setOpen(false);
    };

    return (
        <>
            <ClickAwayListener onClickAway={handleClickAway}>
                <div className={classes.root}>
                    <IconButton
                        onClick={handleClick}
                        onMouseEnter={() => handleHoverAction?.(HoverActions.SHARE)}
                        onMouseLeave={handleLeaveAction}
                        size={isFullTweet ? "medium" : "small"}
                    >
                        <>{ShareIcon}</>
                        <HoverAction visible={visibleShareAction} actionText={"Share"}/>
                    </IconButton>
                    {open ? (
                        <div className={classnames(classes.dropdown, globalClasses.svg)}>
                            <List>
                                <ListItem onClick={onClickSendViaDirectMessage}>
                                    <>{MessagesIcon}</>
                                    <Typography variant={"body1"} component={"span"}>
                                        Send via Direct Message
                                    </Typography>
                                </ListItem>
                                <ListItem onClick={onClickAddTweetToBookmarks}>
                                    <>{AddBookmarksIcon}</>
                                    <Typography variant={"body1"} component={"span"}>
                                        {tweet.isTweetBookmarked ? "Remove Tweet from Bookmarks" : "Add Tweet to Bookmarks"}
                                    </Typography>
                                </ListItem>
                                <CopyToClipboard text={CLIENT_URL + location.pathname}>
                                    <ListItem onClick={onCopyLinkToTweet}>
                                        <>{LinkIcon}</>
                                        <Typography variant={"body1"} component={"span"}>
                                            Copy link to Tweet
                                        </Typography>
                                    </ListItem>
                                </CopyToClipboard>
                                <ListItem>
                                    <>{ShareIcon}</>
                                    <Typography variant={"body1"} component={"span"}>
                                        Share Tweet via ...
                                    </Typography>
                                </ListItem>
                            </List>
                        </div>
                    ) : null}
                    <SendDirectTweetModal
                        tweet={tweet}
                        visible={visibleSendDirectTweetModal}
                        onSendDirectTweet={onSendDirectTweet}
                        closeShareTweet={closeShareTweet}
                        onClose={onCloseSendViaDirectMessage}
                    />
                    <ActionSnackbar
                        snackBarMessage={snackBarMessage!}
                        openSnackBar={openSnackBar!}
                        onCloseSnackBar={onCloseSnackBar!}
                    />
                </div>
            </ClickAwayListener>
        </>
    );
};

export default withSnackbar(ShareTweet);
