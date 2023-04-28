import React, {FC, ReactElement} from 'react';
import Grid from '@material-ui/core/Grid';
import {Container, createStyles, InputBase, makeStyles, Paper, Typography, withStyles} from "@material-ui/core";
import grey from "@material-ui/core/colors/grey";

import Tweet from "../components/Tweet/Tweet";
import SideMenu from "../components/SideMenu/SideMenu";

export const useHomeStyles = makeStyles((theme) => ({
    wrapper: {
        height: '100vh'
    },
    logo: {
        margin: '10px 10px'
    },
    logoIcon: {
        fontSize: 38
    },
    sideMenuList: {
        listStyle: 'none',
        padding: 0,
        margin: '20px 0 0 0',
        width: 230
    },
    sideMenuListItem: {
        '& div': {
            display: 'inline-flex',
            alignItems: 'center',
            borderRadius: 15,
            padding: '0 25px 0 20px',
            marginBottom: 15,
            height: 50,
            cursor: 'pointer',
            transition: 'background-color 0.3s ease-in-out'
        },
        '&:hover': {
            '& div': {
                backgroundColor: 'rgba(29, 161, 242, 0.1)',
                '& h6': {
                    color: theme.palette.primary.main
                },
                '& svg path': {
                    color: theme.palette.primary.main
                }
            }
        }
    },
    sideMenuListItemLabel: {
        fontWeight: 700,
        fontSize: 20,
        marginLeft: 15
    },
    sideMenuListItemIcon: {
        fontSize: 28,
        marginLeft: -5
    },
    sideMenuTweetButton: {
        fontSize: 20,
        padding: theme.spacing(3.3),
        marginTop: theme.spacing(2)
    },
    tweetsWrapper: {
        marginTop: 10,
        borderRadius: 0,
        height: '100%',
        borderTop: 0,
        borderBottom: 0,
    },
    tweetsHeader: {
        borderRadius: 0,
        borderTop: 0,
        borderLeft: 0,
        borderRight: 0,
        padding: '10px 15px',
        '& h6': {
            fontWeight: 800
        },
    },
    tweetFooter: {
        display: 'flex',
        position: 'relative',
        left: -13,
        justifyContent: 'space-between',
        width: 450
    },
    tweetUserName: {
        color: grey[500]
    },
    tweet: {
        cursor: 'pointer',
        paddingTop: 15,
        paddingLeft: 20,
        '&:hover': {
            backgroundColor: 'rgb(245, 248, 250)'
        }
    },
    tweetAvatar: {
        width: theme.spacing(5),
        height: theme.spacing(5),
    }
}));

const SearchTextField = withStyles(() =>
    createStyles({
        input: {
            marginTop: 10,
            borderRadius: 10,
            backgroundColor: '#e1edf5'
        }
    })
)(InputBase);

const Home: FC = (): ReactElement => {
    const classes = useHomeStyles();

    return (
        <Container className={classes.wrapper} maxWidth="lg">
            <Grid container spacing={2}>
                <Grid item xs={3}>
                    <SideMenu classes={classes} />
                </Grid>
                <Grid item xs={6}>
                    <Paper className={classes.tweetsWrapper} variant="outlined">
                        <Paper className={classes.tweetsHeader} variant="outlined">
                            <Typography variant="h6">Home</Typography>
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
                <Grid item xs={3}>
                    <SearchTextField placeholder="Search Twitter"/>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Home;
