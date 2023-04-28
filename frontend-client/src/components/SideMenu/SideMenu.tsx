import React, {FC, ReactElement} from 'react';
import {Button, Hidden, IconButton, Typography} from "@material-ui/core";
import TwitterIcon from "@material-ui/icons/Twitter";
import SearchIcon from "@material-ui/icons/Search";
import NotificationIcon from "@material-ui/icons/NotificationsOutlined";
import MessageIcon from "@material-ui/icons/MailOutlineOutlined";
import BookmarkIcon from "@material-ui/icons/BookmarkBorderOutlined";
import ListIcon from "@material-ui/icons/ListAltOutlined";
import PersonIcon from "@material-ui/icons/PersonOutlineOutlined";
import {useHomeStyles} from "../../pages/Home/HomeStyles";
import {Create} from "@material-ui/icons";
import ModalBlock from "../ModalBlock/ModalBlock";
import {AddTweetForm} from "../TweetForm/AddTweetForm";
import {Link} from "react-router-dom";


interface SideMenuProps {
    classes: ReturnType<typeof useHomeStyles>
}

const SideMenu: FC<SideMenuProps> = ({classes}: SideMenuProps): ReactElement => {
    const [visibleAddTweet, setSetVisibleAddTweet] = React.useState<boolean>(false);

    const handleClickOpenAddTweet = () => {
        setSetVisibleAddTweet(true);
    };

    const onCloseAddTweet = () => {
        setSetVisibleAddTweet(false);
    };

    return (
        <ul className={classes.sideMenuList}>
            <li className={classes.sideMenuListItem}>
                <Link to={"/home"}>
                    <IconButton className={classes.logo} color="primary">
                        <TwitterIcon className={classes.logoIcon}/>
                    </IconButton>
                </Link>
            </li>
            <li className={classes.sideMenuListItem}>
                <div>
                    <SearchIcon className={classes.sideMenuListItemIcon}/>
                    <Hidden smDown>
                        <Typography className={classes.sideMenuListItemLabel} variant="h6">
                            Explore
                        </Typography>
                    </Hidden>
                </div>
            </li>
            <li className={classes.sideMenuListItem}>
                <div>
                    <NotificationIcon className={classes.sideMenuListItemIcon}/>
                    <Hidden smDown>
                        <Typography className={classes.sideMenuListItemLabel} variant="h6">
                            Notification
                        </Typography>
                    </Hidden>
                </div>
            </li>
            <li className={classes.sideMenuListItem}>
                <div>
                    <MessageIcon className={classes.sideMenuListItemIcon}/>
                    <Hidden smDown>
                        <Typography className={classes.sideMenuListItemLabel} variant="h6">
                            Messages
                        </Typography>
                    </Hidden>
                </div>
            </li>
            <li className={classes.sideMenuListItem}>
                <div>
                    <BookmarkIcon className={classes.sideMenuListItemIcon}/>
                    <Hidden smDown>
                        <Typography className={classes.sideMenuListItemLabel} variant="h6">
                            Bookmarks
                        </Typography>
                    </Hidden>
                </div>
            </li>
            <li className={classes.sideMenuListItem}>
                <div>
                    <ListIcon className={classes.sideMenuListItemIcon}/>
                    <Hidden smDown>
                        <Typography className={classes.sideMenuListItemLabel} variant="h6">
                            Lists
                        </Typography>
                    </Hidden>
                </div>
            </li>
            <li className={classes.sideMenuListItem}>
                <div>
                    <PersonIcon className={classes.sideMenuListItemIcon}/>
                    <Hidden smDown>
                        <Typography className={classes.sideMenuListItemLabel} variant="h6">
                            Profile
                        </Typography>
                    </Hidden>
                </div>
            </li>
            <li className={classes.sideMenuListItem}>
                <Button
                    onClick={handleClickOpenAddTweet}
                    className={classes.sideMenuTweetButton}
                    variant="contained"
                    color="primary"
                    fullWidth>
                    <Hidden smDown>
                        Tweet
                    </Hidden>
                    <Hidden mdUp>
                        <Create />
                    </Hidden>
                </Button>
                <ModalBlock onClose={onCloseAddTweet} visible={visibleAddTweet}>
                    <div style={{width: 550}}>
                        <AddTweetForm maxRows={15} classes={classes}/>
                    </div>
                </ModalBlock>
            </li>
        </ul>
    );
};

export default SideMenu;
