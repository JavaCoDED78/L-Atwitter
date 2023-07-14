import {makeStyles, Theme} from "@material-ui/core";
import {Retweet} from "../../store/ducks/tweets/contracts/state";

interface QuoteTweetStyles {
    isTweetRetweetedByMe?: Retweet;
}

export const useQuoteTweetStyles = makeStyles<Theme, QuoteTweetStyles>((theme) => ({
    footerIcon: {
        "& .MuiIconButton-root": {
            padding: 7,
            "& svg" : {
                color: props => props.isTweetRetweetedByMe ? "rgb(23, 191, 99)" : "rgb(83, 100, 113)",
                verticalAlign: "bottom",
                height: "0.80em",
            }
        },
        "& #retweets": {
            color: props => props.isTweetRetweetedByMe ? "rgb(23, 191, 99)" : "rgb(83, 100, 113)",
        },
    },
    dropdown: {
        padding: 0,
        position: 'absolute',
        width: 160,
        height: 104,
        top: 10,
        left: 20,
        zIndex: 2,
        borderRadius: 4,
        backgroundColor: theme.palette.background.paper,
        boxShadow: "rgb(101 119 134 / 20%) 0px 0px 15px, rgb(101 119 134 / 15%) 0px 0px 3px 1px",
        "& .MuiList-root": {
            padding: 0,
        },
        '& .MuiListItem-root': {
            height: 52,
            '&:hover': {
                cursor: 'pointer',
                backgroundColor: 'rgb(247, 249, 249)',
            },
        },
    },
    text: {
        fontSize: 15,
        fontWeight: 400,
    },
    textIcon: {
        "& svg": {
            verticalAlign: "bottom",
            marginRight: 15,
            fill: "rgb(83, 100, 113)",
            height: "1.30em",
        },
    },
}));
