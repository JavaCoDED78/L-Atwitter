import React, {FC, ReactElement} from 'react';
import {
    Avatar, Button,
    Container,
    Divider,
    InputAdornment,
    List, ListItem, ListItemAvatar, ListItemText,
    Paper,
    Typography,
    Grid,
} from "@material-ui/core";

import Tweet from "../../components/Tweet/Tweet";
import SideMenu from "../../components/SideMenu/SideMenu";
import {useHomeStyles} from "./HomeStyles";
import SearchIcon from "@material-ui/icons/Search";
import {PersonAddOutlined} from "@material-ui/icons";
import {SearchTextField} from "../../components/Tweet/SearchTextField";
import {AddTweetForm} from "../../components/TweetForm/AddTweetForm";

const Home: FC = (): ReactElement => {
    const classes = useHomeStyles();

    return (
        <Container className={classes.wrapper} maxWidth="lg">
            <Grid container spacing={3}>
                <Grid item sm={1} md={3}>
                    <SideMenu classes={classes} />
                </Grid>
                <Grid item sm={8} md={6}>
                    <Paper className={classes.tweetsWrapper} variant="outlined">
                        <Paper className={classes.tweetsHeader} variant="outlined">
                            <Typography variant="h6">Home</Typography>
                        </Paper>
                        <Paper>
                            <div className={classes.addForm}>
                                <AddTweetForm classes={classes} />
                            </div>
                            <div className={classes.addFormBottomLine}/>
                        </Paper>
                        <Tweet
                            classes={classes}
                            text={"Zero! Zero! Zero! Zero!"}
                            user={{
                                fullname: "Default Default",
                                username: "default",
                                avatarUrl: "https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg"
                            }}/>
                    </Paper>
                </Grid>
                <Grid item sm={3} md={3}>
                    <div className={classes.rightSide}>
                        <SearchTextField
                            variant="outlined"
                            placeholder="Search Twitter"
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <SearchIcon />
                                    </InputAdornment>
                                ),
                            }}
                            fullWidth
                        />
                        <Paper className={classes.rightSideBlock}>
                            <Paper className={classes.rightSideBlockHeader} variant="outlined">
                                <b>Topics</b>
                            </Paper>
                            <List>
                                <ListItem className={classes.rightSideBlockItem}>
                                    <ListItemText
                                        primary="Minsk"
                                        secondary={
                                            <Typography component="span" variant="body2" color="textSecondary">
                                                Tweets: 5 000
                                            </Typography>
                                        }
                                    />
                                </ListItem>
                                <Divider component="li"/>
                                <ListItem className={classes.rightSideBlockItem}>
                                    <ListItemText
                                        primary="#fucking"
                                        secondary={
                                            <Typography component="span" variant="body2" color="textSecondary">
                                                Tweets: 10 000
                                            </Typography>
                                        }
                                    />
                                </ListItem>
                                <Divider component="li"/>
                                <ListItem className={classes.rightSideBlockItem}>
                                    <ListItemText
                                        primary="Belarus"
                                        secondary={
                                            <Typography component="span" variant="body2" color="textSecondary">
                                                Tweets: 50 000
                                            </Typography>
                                        }
                                    />
                                </ListItem>
                                <Divider component="li"/>
                            </List>
                        </Paper>
                        <Paper className={classes.rightSideBlock}>
                            <Paper className={classes.rightSideBlockHeader} variant="outlined">
                                <b>Who read</b>
                            </Paper>
                            <List>
                                <ListItem className={classes.rightSideBlockItem}>
                                    <ListItemAvatar>
                                        <Avatar
                                            alt="Current Current"
                                            src="https://cs8.pikabu.ru/post_img/2016/05/27/5/1464332742115529028.jpg"
                                        />
                                    </ListItemAvatar>
                                    <ListItemText
                                        primary="Ba la bol"
                                        secondary={
                                            <Typography component="span" variant="body2" color="textSecondary">
                                                @Balabol
                                            </Typography>
                                        }
                                    />
                                    <Button color="primary">
                                        <PersonAddOutlined />
                                    </Button>
                                </ListItem>
                                <Divider component="li"/>
                            </List>
                        </Paper>
                    </div>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Home;
