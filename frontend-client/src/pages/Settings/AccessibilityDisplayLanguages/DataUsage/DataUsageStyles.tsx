import {makeStyles, Theme} from "@material-ui/core";

export const useDataUsageStyles = makeStyles((theme: Theme) => ({
    infoItemWrapper: {
        padding: "12px 16px"
    },
    text: {
        color: "rgb(83, 100, 113)",
        fontWeight: 400,
        fontSize: 13,
        lineHeight: "16px"
    },
    infoItem: {
        paddingBottom: 12,
        fontSize: 15,
        color: "rgb(15, 20, 25)",
        fontWeight: 400,
        lineHeight: "20px",
        "& .MuiCheckbox-root": {
            float: "right",
            marginTop: -10,
        },
    },
    accessibilityWrapper: {
        textDecoration: "none",
    },
    accessibilityLink: {
        padding: "12px 16px",
        fontSize: 15,
        color: "rgb(15, 20, 25)",
        fontWeight: 400,
        lineHeight: "20px",
        "&:hover": {
            backgroundColor: "rgb(247, 249, 249)",
            cursor: "pointer"
        },
        "& svg": {
            marginTop: 8,
            float: "right",
            color: "rgb(83, 100, 113)",
            height: "1.4em"
        },
    },
    accessibilityInfo: {
        display: "inline-block",
        width: 200
    },
}));
