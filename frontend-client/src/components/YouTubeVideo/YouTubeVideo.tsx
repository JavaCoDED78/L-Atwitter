import React, {FC, ReactElement} from 'react';
import {Typography} from "@material-ui/core";

import {useYouTubeVideoStyles} from "./YouTubeVideoStyles";
import {LinkIcon} from "../../icons";
import {TweetResponse} from "../../store/types/tweet";

interface YouTubeVideoProps {
    tweet: TweetResponse;
}

const YouTubeVideo: FC<YouTubeVideoProps> = ({tweet}): ReactElement => {
    const classes = useYouTubeVideoStyles();

    const getYouTubeVideoId = (url: string): string | null => {
        const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
        const match = url.match(regExp);

        return (match && match[2].length === 11) ? match[2] : null;
    };

    return (
        <div className={classes.container}>
            <iframe
                src={`https://www.youtube.com/embed/${getYouTubeVideoId(tweet.link)}`}
                title="YouTube video player" frameBorder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
            >
            </iframe>
            <a className={classes.youtubeLink} target="_blank" href={tweet.link}>
                <div className={classes.videoInfoWrapper}>
                    <Typography variant={"body1"}  component={"div"}>
                        {tweet.linkTitle}
                    </Typography>
                    <Typography variant={"subtitle1"} component={"div"}>
                        {tweet.linkDescription}
                    </Typography>
                    <Typography variant={"subtitle1"} component={"div"}>
                        {LinkIcon}youtube.com
                    </Typography>
                </div>
            </a>
        </div>
    );
};

export default YouTubeVideo;
