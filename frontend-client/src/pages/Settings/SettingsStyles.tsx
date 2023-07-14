import {makeStyles, Theme} from "@material-ui/core";

export const useSettingsStyles = makeStyles((theme: Theme) => ({
    grid: {
        padding: "12px 0px 0px 0px !important",
    },
    container: {
        "& .MuiPaper-outlined": {
            padding: 0,
            borderRadius: 0,
            minHeight: '100vh',
            borderTop: 0,
            borderBottom: 0,
        },
    },
    leftSideHeader: {
        position: "fixed",
        display: 'flex',
        margin: 0,
        padding: 0,
        width: 416,
        height: 53,
        zIndex: 1,
        borderTop: 0,
        borderLeft: 0,
        borderRight: 0,
        borderRadius: 0,
        alignItems: 'center',
        flex: 1,
        '& h6': {
            marginLeft: 15,
            fontWeight: 800,
        },
    },
    rightSideHeader: {
        position: "fixed",
        display: 'flex',
        margin: 0,
        padding: 0,
        width: 599,
        height: 53,
        zIndex: 1,
        borderTop: 0,
        borderLeft: 0,
        borderRight: 0,
        borderRadius: 0,
        alignItems: 'center',
        flex: 1,
        '& h6': {
            marginLeft: 15,
            fontWeight: 800,
        },
    },
    listWrapper: {
        paddingTop: 53,
        "& a": {
            textDecoration: "none"
        },
        "& .MuiList-root": {
            padding: 0,
        },
        "& .MuiListItem-root": {
            padding: "14px 16px",
            "&:hover": {
                cursor: "pointer",
                backgroundColor: "rgb(247, 249, 249)",
            },
        },
        "& .MuiTypography-root": {
            fontSize: 15,
            fontWeight: 400,
            lineHeight: "20px",
            color: "rgb(15, 20, 25)",
        },
        "& .Mui-selected": {
            borderRight: "2px solid rgb(27, 149, 224)",
            "& svg": {
                marginRight: "-2px"
            },
        },
        "& svg": {
            color: "rgb(83, 100, 113)",
            height: "1.4em",
            marginLeft: "auto",
        },
    },
    pageContainer: {
        minWidth: 600,
        "& .MuiPaper-outlined": {
            padding: 0,
            borderRadius: 0,
            minHeight: '100vh',
            borderLeft: 0,
            borderTop: 0,
            borderBottom: 0,
        },
    },
    pageInfoWrapper: {
        paddingTop: 53
    },
    pageInfoItemWrapper: {
        padding: "12px 16px"
    },
}));
