import React, {FC, memo, ReactElement} from "react";
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";
import {Avatar} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {ClassNameMap} from "@material-ui/core/styles/withStyles";
import classnames from "classnames";

import {PROFILE} from "../../../util/pathConstants";
import LinkWrapper from "../../LinkWrapper/LinkWrapper";
import PopperUserWindow from "../../PopperUserWindow/PopperUserWindow";
import {
    selectTweetUserAvatar,
    selectTweetUserFullName,
    selectTweetUserId,
    selectTweetUserUsername
} from "../../../store/ducks/tweet/selectors";
import {useGlobalStyles} from "../../../util/globalClasses";
import {useHoverUser} from "../../../hook/useHoverUser";

interface TweetHeaderProps {
    classes: ClassNameMap<string>
}

const TweetHeader: FC<TweetHeaderProps> = memo(({classes}): ReactElement => {
    const globalClasses = useGlobalStyles();
    const {visiblePopperWindow, handleHoverPopper, handleLeavePopper} = useHoverUser();
    const tweetUserId = useSelector(selectTweetUserId);
    const avatar = useSelector(selectTweetUserAvatar);
    const userFullName = useSelector(selectTweetUserFullName);
    const userUsername = useSelector(selectTweetUserUsername);

    return (
        <div className={classes.header}>
            <Link to={`${PROFILE}/${tweetUserId}`}>
                <Avatar
                    className={classnames(globalClasses.avatar, classes.avatar)}
                    alt={`avatar ${tweetUserId}`}
                    src={avatar}
                />
            </Link>
            <LinkWrapper path={`${PROFILE}/${tweetUserId}`} visiblePopperWindow={visiblePopperWindow}>
                <div
                    id={"userInfo"}
                    onMouseEnter={() => handleHoverPopper(tweetUserId!)}
                    onMouseLeave={handleLeavePopper}
                >
                    <Typography variant={"h6"} component={"div"} id={"link"}>
                        {userFullName}
                    </Typography>
                    <Typography variant={"subtitle1"} component={"div"}>
                        @{userUsername}
                    </Typography>
                    <PopperUserWindow visible={visiblePopperWindow} isTweetImageModal={true}/>
                </div>
            </LinkWrapper>
        </div>
    );
});

export default TweetHeader;
