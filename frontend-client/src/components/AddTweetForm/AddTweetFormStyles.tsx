import {makeStyles, Theme} from "@material-ui/core";
import {Tweet} from "../../store/ducks/tweets/contracts/state";

interface AddTweetFormStyles {
    quoteTweet?: Tweet;
    isScheduled?: boolean;
}

export const useAddTweetFormStyles = makeStyles<Theme, AddTweetFormStyles>((theme) => ({
    content: {
        display: 'flex',
        width: '100%',
    },
    contentAvatar: {
        width: "46px !important",
        height: "46px !important",
        marginRight: 15,
    },
    infoWrapper: {
        marginBottom: 10,
        "& svg": {
            verticalAlign: "bottom",
            marginRight: 12,
            fill: "rgb(83, 100, 113)",
            height: "1.30em",
        },
    },
    text: {
        fontSize: 13,
        fontWeight: 400,
        lineHeight: "16px",
        color: "rgb(83, 100, 113)",
    },
    contentTextarea: {
        width: '100%',
        border: 0,
        fontSize: 20,
        outline: 'none',
        fontFamily: 'inherit',
        resize: 'none',
    },
    image: {
        position: 'relative',
        '& img': {
            marginLeft: "58px",
            objectFit: "cover",
            marginTop: 10,
            width: 504,
            height: 280,
            borderRadius: 20,
            borderColor: "#5b7083",
        },
        "& svg": {
            verticalAlign: "top",
            fill: '#fff',
            height: "0.75em",
        },
    },
    imageSmall: {
        position: 'relative',
        '& img': {
            marginLeft: 58,
            objectFit: "cover",
            marginTop: 10,
            width: 260,
            height: 152,
            borderRadius: 20,
            borderColor: "#5b7083",
        },
        "& svg": {
            verticalAlign: "top",
            fill: '#fff',
            height: "0.75em",
        },
    },
    imageRemove: {
        padding: 6,
        top: 15,
        left: 65,
        position: 'absolute',
        backgroundColor: '#322C28 !important',
    },
    footer: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    footerWrapper: {
        display: 'flex',
        position: 'relative',
        paddingTop: 5,
        paddingBottom: 5,
        left: -13,
        justifyContent: 'space-between',
        maxWidth: 450,
        marginTop: 10,
        paddingLeft: 70,
    },
    footerImage: {
        "& .MuiIconButton-root": {
            padding: 7,
            "& svg": {
                verticalAlign: "bottom",
                height: "0.9em",
            },
        },
    },
    quoteImage: {
        "& .MuiIconButton-root": {
            padding: 7,
            "& svg": {
                color: props => props.quoteTweet || props.isScheduled ? "rgb(142, 205, 247)" : theme.palette.primary.main,
                verticalAlign: "bottom",
                height: "0.9em",
            },
        },
    },
    footerAddForm: {
        display: 'flex',
        alignItems: 'center',
    },
    footerAddFormCircleProgress: {
        position: 'relative',
        width: 20,
        height: 20,
        margin: '0 10px',
        '& .MuiCircularProgress-root': {
            position: 'absolute',
        },
    },
    snackBar: {
        "& .MuiSnackbarContent-root": {
            minWidth: 179,
            height: 44,
            padding: "0px 10px",
            backgroundColor: "rgb(29, 161, 242)",
            "& .MuiSnackbarContent-message": {
                fontSize: 15,
                margin: "0 auto",
                textAlign: "center",
            },
        },
    },
}));
