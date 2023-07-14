import {makeStyles, Theme} from "@material-ui/core";

export const useSuggestedListsStyles = makeStyles((theme: Theme) => ({
    container: {
        borderRadius: 0,
        minHeight: '100vh',
        borderTop: '0',
        borderBottom: '0',
        marginBottom: 500,
    },
    loading: {
        paddingTop: 50,
        textAlign: 'center',
    },
    header: {
        position: "fixed",
        width: 602,
        height: 52,
        zIndex: 1,
        display: 'flex',
        alignItems: 'center',
        flex: 1,
        borderTop: 0,
        borderLeft: 0,
        borderRight: 0,
        borderRadius: 0,
        '& h6': {
            fontWeight: 800,
        },
    },
    content: {
        borderTop: 0,
        borderLeft: 0,
        borderRight: 0,
        borderRadius: 0,
    },
    contentImage: {
        paddingTop: 52,
        inset: 0,
        width: "100%",
        height: "100%",
    },
    infoWrapper: {
        textAlign: "center",
        padding: 32,
    },
    infoTitle: {
        fontSize: 23,
        fontWeight: 700,
        marginBottom: 8,
    },
    infoText: {
        fontSize: 15,
        color: "rgb(83, 100, 113)"
    },
    listsTitle: {
        padding: "12px 16px",
        fontSize: 20,
        fontWeight: 800,
    },
}));
