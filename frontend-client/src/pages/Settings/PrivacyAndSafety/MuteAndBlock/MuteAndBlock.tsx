import React, {FC, ReactElement} from 'react';
import {Link} from 'react-router-dom';
import {Typography} from "@material-ui/core";

import {useMuteAndBlockStyles} from "./MuteAndBlockStyles";
import {ArrowRightIcon} from "../../../../icons";

const MuteAndBlock: FC = (): ReactElement => {
    const classes = useMuteAndBlockStyles();

    return (
        <>
            <div className={classes.infoItemWrapper}>
                <Typography component={"div"} className={classes.text}>
                    Manage the accounts, words, and notifications that you’ve muted or blocked.
                </Typography>
            </div>
            <div className={classes.linkWrapper}>
                <Link to={"/settings/privacy_and_safety/blocked"}>
                    <div className={classes.contentLink}>
                        <Typography component={"span"}>
                            Blocked accounts
                        </Typography>
                        {ArrowRightIcon}
                    </div>
                </Link>
                <Link to={"/settings/privacy_and_safety/muted"}>
                    <div className={classes.contentLink}>
                        <Typography component={"span"}>
                            Muted accounts
                        </Typography>
                        {ArrowRightIcon}
                    </div>
                </Link>
                <Link to={"/settings/privacy_and_safety/muted_keywords"}>
                    <div className={classes.contentLink}>
                        <Typography component={"span"}>
                            Muted words
                        </Typography>
                        {ArrowRightIcon}
                    </div>
                </Link>
                <Link to={"/settings/privacy_and_safety/advanced_filters"}>
                    <div className={classes.contentLink}>
                        <Typography component={"span"}>
                            Muted notifications
                        </Typography>
                        {ArrowRightIcon}
                    </div>
                </Link>
            </div>
        </>
    );
};

export default MuteAndBlock;
