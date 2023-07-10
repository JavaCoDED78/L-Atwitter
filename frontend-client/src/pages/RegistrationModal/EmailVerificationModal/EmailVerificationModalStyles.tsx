import {makeStyles, Theme} from "@material-ui/core";

export const useEmailVerificationModalStyles = makeStyles((theme: Theme) => ({
    container: {
        width: 550,
        height: 600,
        marginTop: 5,
        padding: "0 30px",
    },
    logoIcon: {
        margin: "0 auto",
        width: 30,
        "& svg": {
            fontSize: 30,
            color: "rgb(29, 161, 245)",
        },
    },
    title: {
        marginTop: 20,
        fontSize: 21,
        fontWeight: 700,
    },
    text: {
        marginTop: 15,
        color: "rgb(83, 100, 113)",
    },
    link: {
        marginLeft: 10,
        marginTop: 2,
        fontSize: 12,
        color: "rgb(27, 149, 224)",
        "&:hover": {
            textDecoration: "underline",
            cursor: "pointer",
        },
    },
}));
