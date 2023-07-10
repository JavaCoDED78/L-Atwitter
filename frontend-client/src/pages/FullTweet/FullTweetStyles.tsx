import {makeStyles, Theme} from "@material-ui/core";
import grey from "@material-ui/core/colors/grey";

export const useFullTweetStyles = makeStyles((theme: Theme) => ({
    retweetWrapper: {
        display: "flex",
        alignItems: "center",
        marginLeft: 20,
        marginTop: 5,
        color: "rgb(83, 100, 113)",
        '& p': {
            marginLeft: 15,
            fontSize: 14,
            fontWeight: 700
        },
        "& svg": {
            verticalAlign: "bottom",
            height: "1.20em",
        },
    },
    loading: {
        marginTop: 50,
        textAlign: 'center',
    },
    container: {
        borderTop: '0',
        borderLeft: '0',
        borderRight: '0',
        padding: "10px 22px 0px 22px",
    },
    header: {
        display: 'flex',
        alignItems: 'center',
        '& a': {
            color: "#000",
            textDecoration: 'none',
        },
    },
    avatar: {
        width: theme.spacing(6.5),
        height: theme.spacing(6.5),
        marginRight: 15,
    },
    username: {
        color: "rgb(83, 100, 113)",
    },
    date: {
        color: "rgb(83, 100, 113)",
    },
    textWrapper: {
        fontSize: 24,
        marginTop: 16,
        marginBottom: 16,
        lineHeight: 1.3125,
        wordBreak: 'break-word',
        '& #hashtag': {
            color: "rgb(27, 149, 224)",
        },
    },
    image: {
        "& img": {
            objectFit: "cover",
            marginTop: 10,
            width: 564,
            borderRadius: 20,
            borderColor: "#5b7083",
        },
    },
    content: {
        display: 'flex',
        alignItems: 'center',
        margin: "16px 0",
        fontSize: 15,
        '& a': {
            color: "#000",
            textDecoration: 'none'
        },
    },
    contentItem: {
        marginLeft: 5,
        color: "rgb(83, 100, 113)",
    },
    info: {
        display: 'flex',
        position: 'relative',
        paddingTop: 5,
        paddingBottom: 5,
        margin: '0 auto',
        borderTop: '1px solid #E6ECF0',
        left: 0,
        maxWidth: '100%',
        justifyContent: 'space-around',
        padding: '2px 0',
        "& svg": {
            fontSize: 25,
        },
    },
    infoIcon: {
        "& .MuiIconButton-root": {
            marginBottom: 6,
            width: 40,
            height: 40,
            color: "#5b7083",
            "& span": {
                paddingBottom: 3,
                "& svg" : {
                    verticalAlign: "bottom",
                    height: "0.9em",
                }
            },
        },
    },
    replyWrapper: {
        margin: "16px 60px",
        color: "rgb(83, 100, 113)",
        fontSize: 15,
        "& a": {
            textDecoration: "none",
            color: "rgb(27, 149, 224)",
        },
    },
    divider: {
        height: 12,
        backgroundColor: '#E6ECF0',
    },
}));
