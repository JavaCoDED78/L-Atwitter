import {makeStyles, Theme} from "@material-ui/core";

export const useAccountAccessHistoryStyles = makeStyles((theme: Theme) => ({
    infoItemWrapper: {
        padding: "12px 16px"
    },
    text: {
        fontSize: 13,
        color: "rgb(83, 100, 113)",
        fontWeight: 400,
        lineHeight: "16px",
    },
    link: {
        textDecoration: "none",
        color: "rgb(29, 155, 240)",
        cursor: "pointer",
        "&:hover": {
            textDecoration: "underline",
        }
    },
}));
