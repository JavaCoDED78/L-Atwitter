import {makeStyles, Theme} from "@material-ui/core";

export const useDisplayStyles = makeStyles((theme: Theme) => ({
    infoItemWrapper: {
        padding: "12px 16px"
    },
    title: {
        fontWeight: 800,
        lineHeight: "24px",
        fontSize: 20,
        color: "rgb(15, 20, 25)",
    },
    text: {
        color: "rgb(83, 100, 113)",
        fontWeight: 400,
        fontSize: 13,
        lineHeight: "16px"
    },
    tweetInfoWrapper: {
        display: "inline-flex",
        justifyContent: "flex-start"
    },
    tweetIconWrapper: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        marginRight: 16,
        width: 48,
        height: 48,
        backgroundColor: "rgb(29, 155, 240)",
        borderRadius: "50%",
    },
    tweetIcon: {
        marginTop: 5,
        "& svg": {
            color: "#FFF",
            height: "1.7em"
        },
    },
    tweetTitle: {
        marginRight: 3,
        fontWeight: 700,
        fontSize: 15,
        color: "rgb(15, 20, 25)",
        lineHeight: "20px"
    },
    tweetVerifiedIcon: {
        marginRight: 3,
        "& svg": {
            verticalAlign: "bottom",
            color: "rgb(29, 155, 240)",
            height: "1.35em"
        },
    },
    tweetInfoText: {
        fontWeight: 400,
        fontSize: 15,
        color: "rgb(83, 100, 113)",
        lineHeight: "20px"
    },
    tweetText: {
        fontWeight: 400,
        fontSize: 15,
        color: "rgb(15, 20, 25)",
        lineHeight: "20px",
        "&#xs": {
            fontSize: 13,
        },
        "&#xl": {
            fontSize: 20,
        },
    },
    tweetLink: {
        color: "rgb(29, 155, 240)",
    },
    divider: {
        height: 1,
        backgroundColor: "rgb(239, 243, 244)",
    },
    stepperWrapper: {
        display: "flex",
        alignItems: "center",
        padding: "16px 0px"
    },
    stepper: {
        position: "relative",
        width: "100%",
        height: 4,
        margin: "0px 20px",
        backgroundColor: "rgb(142, 205, 248)",

    },
    stepperPoint: {
        marginTop: -3,
        position: "absolute",
        verticalAlign: "baseline",
        width: 12,
        height: 12,
        borderRadius: "50%",
        backgroundColor: "rgb(29, 155, 240)",
        boxShadow: "rgb(101 119 134 / 20%) 0px 0px 7px, rgb(101 119 134 / 15%) 0px 1px 3px 1px",
        "&#xs": {
            marginLeft: "1%",
        },
        "&#sm": {
            marginLeft: "24%",
        },
        "&#md": {
            marginLeft: "49%",
        },
        "&#lg": {
            marginLeft: "74%",
        },
        "&#xl": {
            marginLeft: "97%",
        },
    },
    colorWrapper: {
        display: "flex",
        justifyContent: "space-around",
        padding: "8px 0px"
    },
    colorItem: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: 45,
        height: 45,
        borderRadius: "50%",
        cursor: "pointer",
        "&#blue": {
            backgroundColor: "rgb(29, 155, 240)",
        },
        "&#yellow": {
            backgroundColor: "rgb(255, 212, 0)",
        },
        "&#crimson": {
            backgroundColor: "rgb(249, 24, 128)",
        },
        "&#violet": {
            backgroundColor: "rgb(120, 86, 255)",
        },
        "&#orange": {
            backgroundColor: "rgb(255, 122, 0)",
        },
        "&#green": {
            backgroundColor: "rgb(0, 186, 124)",
        },
    },
    checkIcon: {
        marginTop: 5,
        "& svg": {
            color: "#FFF",
            height: "1.9em"
        },
    },
    backgroundContainer: {
        padding: "4px 12px"
    },
    backgroundWrapper: {
        display: "inline-block",
    },
    backgroundItem: {
        display: "flex",
        alignItems: "center",
        justifyContent: "flex-start",
        width: 181,
        height: 60,
        padding: "0px 20px",
        margin: 4,
        border: "2px solid rgb(29, 155, 240)",
        borderRadius: 4,
        cursor: "pointer",
        "&#default": {
            backgroundColor: "rgb(255, 255, 255)",
            color: "rgb(15, 20, 25)",
        },
        "&#dim": {
            backgroundColor: "rgb(21, 32, 43)",
            color: "rgb(255, 255, 255)",
        },
        "&#lights-out": {
            backgroundColor: "rgb(0, 0, 0)",
            color: "rgb(217, 217, 217)",
        },
    },
    backgroundItemWrapper: {
        padding: "4px 0px",
        "& .MuiTypography-root": {
            fontSize: 15,
            color: "rgb(15, 20, 25)",
            fontWeight: 400,
            lineHeight: "20px",
        },
        "& .MuiButtonBase-root": {
            padding: 4,
            "& .MuiSvgIcon-root": {
                width: 20,
                height: 20
            },
        },
    },
    backgroundItemText: {
        fontWeight: 700,
        fontSize: 15,
        lineHeight: "20px",
        marginLeft: 25,
    },
}));
