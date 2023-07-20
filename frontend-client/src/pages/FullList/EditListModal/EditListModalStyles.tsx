import {makeStyles, Theme} from "@material-ui/core";

export const useEditListModalStyles = makeStyles((theme: Theme) => ({
    dialog: {
        "& .MuiDialogTitle-root": {
            marginBottom: 0,
        },
    },
    content: {
        height: 569,
        width: 598,
        padding: "0px 0px",
        overflowX: "hidden",
    },
    button: {
        marginLeft: "auto",
    },
    wallpaperWrapper: {
        width: 598,
        height: 200,
        backgroundColor: "#B2B2B2",
        position: "relative",
        zIndex: 1,
    },
    wallpaperImg: {
        objectFit:"cover",
        position: "absolute",
        zIndex: 1,
        width: 598,
        height: 200,
    },
    wallpaperEditImg: {
        zIndex: 5,
        position: "absolute",
        top: "42%",
        left: "46%",
    },
    footer: {
        padding: "12px 16px",
    },
    footerWrapper: {
        display: "flex",
        justifyContent: "space-between",
    },
    manageMembers: {
        display: "flex",
        justifyContent: "space-between",
        borderTop: `1px solid ${theme.palette.divider}`,
        padding: "12px 16px",
        "&:hover": {
            cursor: "pointer",
            backgroundColor: theme.palette.secondary.main,
        },
        "& svg": {
            fill: theme.palette.text.secondary,
            height: "1.20em",
        },
    },
    deleteList: {
        padding: "12px 16px",
        color: theme.palette.error.main,
        border: 0,
        borderRadius: "0px 0px 16px 16px",
        textAlign: "center",
        "&:hover": {
            cursor: "pointer",
            backgroundColor: "rgba(244, 33, 46, 0.1)",
        },
    },
}));
