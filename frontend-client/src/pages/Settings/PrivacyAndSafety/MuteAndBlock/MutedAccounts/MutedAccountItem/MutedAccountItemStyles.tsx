import {makeStyles, Theme} from "@material-ui/core";

interface MutedAccountItemStylesProps {
    isUserMuted: boolean;
}

export const useMutedAccountItemStyles = makeStyles<Theme, MutedAccountItemStylesProps>((theme) => ({
    container: {
        borderTop: 0,
        borderLeft: 0,
        borderRight: 0,
        borderRadius: 0,
        display: "flex",
        alignItems: 'flex-start',
        paddingLeft: 15,
        paddingTop: 8,
        paddingBottom: 8,
        cursor: 'pointer',
        "& .MuiPaper-outlined": {
            minHeight: "1vh",
        },
        '&:hover': {
            backgroundColor: theme.palette.secondary.main,
        },
    },
    listAvatar: {
        marginRight: 15,
    },
    userInfo: {
        position: "relative",
        maxWidth: 350
    },
    userInfoWrapper: {
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between"
    },
    muteButton: {
        marginRight: 16,
        "& .MuiIconButton-root": {
            padding: 7,
            borderColor: props => props.isUserMuted ? theme.palette.error.light : "rgb(207, 217, 222)",
            border: "1px solid",
            borderRadius: "50%",
            "& svg": {
                color: props => props.isUserMuted ? theme.palette.error.main : theme.palette.primary.main,
                height: "0.85em",
            },
            "&:hover": {
                backgroundColor: props => props.isUserMuted ? "rgba(244, 33, 46, 0.1)" : "rgba(29, 155, 240, 0.1)",
            },
        },
    },
}));
