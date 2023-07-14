import {makeStyles, Theme} from "@material-ui/core";

export const useListsItemStyles = makeStyles((theme: Theme) => ({
    container: {
        borderTop: 0,
        borderLeft: 0,
        borderRight: 0,
        borderRadius: 0,
        display: "flex",
        alignItems: 'flex-start',
        padding: "12px 16px",
        cursor: 'pointer',
        '&:hover': {
            backgroundColor: 'rgb(245, 248, 250)',
        },
    },
    link: {
        textDecoration: "none",
    },
    listAvatar: {
        width: "50px !important",
        height: "50px !important",
        borderRadius: 12,
        marginRight: 15,
    },
    listInfoContainer: {
        width: "100%",
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between"
    },
    listInfoWrapper: {
        display: "block",
    },
    listTitle: {
        fontSize: 15,
        fontWeight: 700,
    },
    listOwnerWrapper: {
        display: "inline-block",
    },
    listOwnerAvatar: {
        marginRight: 4,
        width: "15px !important",
        height: "15px !important",
    },
    listOwnerInfoWrapper: {
        display: "inline-block",
        fontSize: 13,
    },
    listOwnerFullName: {
        marginRight: 4,
        fontWeight: 700,
    },
    listOwnerUsername: {
        paddingBottom: 5,
        color: "rgb(83, 100, 113)",
    },
    listPinWrapper: {
        "& .MuiIconButton-root": {
            padding: 7,
            "& svg": {
                verticalAlign: "bottom",
                color: "rgba(29, 161, 242, 1.00)",
                height: "0.85em",
            },
        },
    },
    listPrimaryButton: {
        width: 105,
        height: 32,
        border: '1px solid',
        borderRadius: '25px',
        padding: '0 15px',
        '&:hover': {
            backgroundColor: 'rgb(202, 32, 85)',
        },
    },
    listOutlinedButton: {
        width: 79,
        height: 32,
        border: '1px solid',
        borderRadius: '25px',
        padding: '0 15px',
        '&:hover': {
            backgroundColor: 'rgb(29, 161, 242, 0.1)',
        },
    },
}));
