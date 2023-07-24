import React, {FC, ReactElement} from "react";
import {Button, Typography} from "@material-ui/core";

import {useStartConversationStyles} from "./StartConversationStyles";

interface StartConversationProps {
    onOpenModalWindow: () => void;
}

const StartConversation: FC<StartConversationProps> = ({onOpenModalWindow}): ReactElement => {
    const classes = useStartConversationStyles();

    return (
        <>
            <Typography variant={"h4"} component={"div"} className={classes.messagesTitle}>
                Send a message, get a message
            </Typography>
            <Typography variant={"subtitle1"} component={"div"} className={classes.messagesText}>
                Direct Messages are private conversations between you and other people on Twitter.
                Share Tweets, media, and more!
            </Typography>
            <Button
                onClick={onOpenModalWindow}
                className={classes.messagesButton}
                variant="contained"
                color="primary"
                size="large"
            >
                Start a conversation
            </Button>
        </>
    );
};

export default StartConversation;
