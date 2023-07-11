import {createStyles, makeStyles, Theme} from "@material-ui/core";

export const useTweetComponentChangeReplyStyles = makeStyles((theme: Theme) => createStyles({
    textListItem: {
        fontSize: 15,
        fontWeight: 400,
    },
    textIconListItem: {
        "& svg": {
            verticalAlign: "bottom",
            marginRight: 15,
            fill: "rgb(83, 100, 113)",
            height: "1.30em",
        },
    },
    dropdown: {
        position: 'absolute',
        width: 320,
        height: 284,
        zIndex: 2,
        borderRadius: 16,
        backgroundColor: theme.palette.background.paper,
        boxShadow: "rgb(101 119 134 / 20%) 0px 0px 15px, rgb(101 119 134 / 15%) 0px 0px 3px 1px",
    },
    infoWrapper: {
        fontSize: 15,
        padding: "16px 16px 0px 16px",
    },
    title: {
        fontWeight: 700,
    },
    text: {
        color: "rgb(83, 100, 113)",
    },
    listItem: {
        fontSize: 15,
        height: 60,
        padding: 0,
        backgroundColor: "#fff",
    },
    iconCircle: {
        marginRight: 12,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        width: 40,
        height: 40,
        borderRadius: "50%",
        backgroundColor: "rgb(29, 155, 240)",
    },
    icon: {
        "& svg": {
            marginTop: 5,
            height: "1.35em",
            fill: "#fff",
        },
    },
    checkIcon: {
        marginLeft: "auto",
        "& svg": {
            color: "rgb(29, 155, 240)",
            marginTop: 5,
            height: "1.3em",
        },
    },
}));
