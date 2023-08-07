import React, { FC, memo, ReactElement, useState } from "react";
import { ClickAwayListener, List, ListItem, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import classnames from "classnames";

import { useIconButtonStyles } from "./QuoteIconButtonSyles";
import { QuoteTweetIcon, RetweetIcon, RetweetOutlinedIcon } from "../../icons";
import QuoteTweetModal from "./QuoteTweetModal/QuoteTweetModal";
import { useGlobalStyles } from "../../util/globalClasses";
import { QuoteTweetResponse, UserTweetResponse } from "../../types/tweet";
import ActionIconButton from "../ActionIconButton/ActionIconButton";
import { retweet } from "../../store/ducks/tweets/actionCreators";
import { useDispatch, useSelector } from "react-redux";
import { selectUserDataId } from "../../store/ducks/user/selectors";

export interface QuoteTweetProps {
    tweetId?: number;
    dateTime?: string;
    text?: string;
    user?: UserTweetResponse;
    isTweetRetweeted?: boolean;
    retweetsCount?: number;
}

const QuoteIconButton: FC<QuoteTweetProps> = memo((
    {
        tweetId,
        dateTime,
        text,
        user,
        isTweetRetweeted,
        retweetsCount
    }
): ReactElement => {
    const globalClasses = useGlobalStyles();
    const classes = useIconButtonStyles({ isTweetRetweetedByMe: isTweetRetweeted });
    const dispatch = useDispatch();
    const params = useParams<{ userId: string }>();
    const myProfileId = useSelector(selectUserDataId);
    const [open, setOpen] = useState<boolean>(false);
    const [visibleAddTweet, setVisibleAddTweet] = useState<boolean>(false);

    const handleClick = (): void => {
        setOpen((prev) => !prev);
    };

    const handleClickAway = (): void => {
        setOpen(false);
    };

    const onClickRetweet = (): void => {
        if (user?.id !== myProfileId) {
            dispatch(retweet({ tweetId: tweetId!, userId: params.userId }));
        }
        setOpen(false);
    };

    const handleClickOpenAddTweet = (): void => {
        setVisibleAddTweet(true);
        setOpen(false);
    };

    const onCloseAddTweet = (): void => {
        setVisibleAddTweet(false);
    };

    return (
        <ClickAwayListener onClickAway={handleClickAway}>
            <div className={classes.footerIcon}>
                <ActionIconButton
                    actionText={isTweetRetweeted ? "Undo Retweet" : "Retweet"}
                    icon={isTweetRetweeted ? RetweetIcon : RetweetOutlinedIcon}
                    onClick={handleClick}
                />
                {(retweetsCount !== 0) && (<span id={"retweets"}>{retweetsCount}</span>)}
                {open && (
                    <div className={classnames(classes.dropdown, globalClasses.svg)}>
                        <List>
                            <ListItem id={"clickRetweet"} onClick={onClickRetweet}>
                                <>{RetweetOutlinedIcon}</>
                                <Typography variant={"body1"} component={"span"}>
                                    {isTweetRetweeted ? ("Undo Retweet") : ("Retweet")}
                                </Typography>
                            </ListItem>
                            <ListItem id={"clickOpenAddTweet"} onClick={handleClickOpenAddTweet}>
                                <>{QuoteTweetIcon}</>
                                <Typography variant={"body1"} component={"span"}>
                                    Quote Tweet
                                </Typography>
                            </ListItem>
                        </List>
                    </div>
                )}
                <QuoteTweetModal
                    quoteTweet={{ id: tweetId, dateTime, text, user } as QuoteTweetResponse}
                    onClose={onCloseAddTweet}
                    visible={visibleAddTweet}
                />
            </div>
        </ClickAwayListener>
    );
});

export default QuoteIconButton;
