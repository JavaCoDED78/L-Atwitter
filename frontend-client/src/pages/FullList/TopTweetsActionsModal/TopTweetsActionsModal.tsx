import React, { ReactElement, useState } from "react";
import { ClickAwayListener, List, ListItem, Typography } from "@material-ui/core";

import { useTopTweetsActionsModalStyles } from "./TopTweetsActionsModalStyles";
import { EditIcon, NotShowIcon, SeeLatestIcon } from "../../../icons";
import ActionIconButton from "../../../components/ActionIconButton/ActionIconButton";

const TopTweetsActionsModal = (): ReactElement => {
    const classes = useTopTweetsActionsModalStyles();
    const [open, setOpen] = useState<boolean>(false);

    const handleClick = (): void => {
        setOpen((prev) => !prev);
    };

    const handleClickAway = (): void => {
        setOpen(false);
    };

    return (
        <>
            <ClickAwayListener onClickAway={handleClickAway}>
                <div className={classes.root}>
                    <ActionIconButton onClick={handleClick} actionText={"More"} icon={EditIcon} />
                    {open && (
                        <div className={classes.dropdown}>
                            <List>
                                <ListItem>
                                    <div className={classes.listItemWrapper}>
                                        <span className={classes.textIcon}>
                                            {SeeLatestIcon}
                                        </span>
                                        <div>
                                            <Typography variant={"body1"} component={"div"}>
                                                See top Tweets
                                            </Typography>
                                            <Typography variant={"subtitle2"} component={"div"}>
                                                You’re seeing top Tweets first. Latest Tweets will show up as they
                                                happen.
                                            </Typography>
                                        </div>
                                    </div>
                                </ListItem>
                                <ListItem>
                                    <div className={classes.listItemWrapper}>
                                        <span className={classes.textIcon}>
                                            {NotShowIcon}
                                        </span>
                                        <div>
                                            <Typography variant={"body1"} component={"div"}>
                                                Don’t show these Tweets in Home
                                            </Typography>
                                            <Typography variant={"subtitle2"} component={"div"}>
                                                Top Tweets from this List will no longer show up in your Home timeline.
                                            </Typography>
                                        </div>
                                    </div>
                                </ListItem>
                            </List>
                        </div>
                    )}
                </div>
            </ClickAwayListener>
        </>
    );
};

export default TopTweetsActionsModal;
