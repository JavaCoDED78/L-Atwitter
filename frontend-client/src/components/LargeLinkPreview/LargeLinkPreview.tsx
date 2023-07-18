import React, {FC, ReactElement} from 'react';
import {Typography} from "@material-ui/core";

import {useLargeLinkPreviewStyles} from "./LargeLinkPreviewStyles";
import {Tweet} from "../../store/ducks/tweets/contracts/state";
import {LinkIcon} from "../../icons";

interface LargeLinkPreviewProps {
    tweet: Tweet;
    isFullTweet?: boolean;
}

const LargeLinkPreview: FC<LargeLinkPreviewProps> = ({tweet, isFullTweet}): ReactElement => {
    const classes = useLargeLinkPreviewStyles({isFullTweet: isFullTweet});
    const matches = tweet.link.match(/^https?\:\/\/([^\/?#]+)(?:[\/?#]|$)/i);
    const domain = matches && matches[1];

    return (
        <a className={classes.siteLink} target="_blank" href={tweet.link}>
            <div className={classes.container}>
                <img
                    className={classes.linkCover}
                    src={tweet.linkCover}
                    alt={tweet.linkCover}
                />
                <div className={classes.siteInfoWrapper}>
                    <Typography variant={"body1"} component={"div"}>
                        {tweet.linkTitle}
                    </Typography>
                    <Typography variant={"subtitle1"} component={"div"}>
                        {tweet.linkDescription}
                    </Typography>
                    <Typography variant={"subtitle1"} component={"div"}>
                        {LinkIcon}{domain}
                    </Typography>
                </div>
            </div>
        </a>
    );
};

export default LargeLinkPreview;
