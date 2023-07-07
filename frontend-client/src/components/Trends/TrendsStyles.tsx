import {makeStyles} from "@material-ui/core";

export const useTrendsStyles = makeStyles((theme) => ({
    loading: {
        marginTop: 50,
        textAlign: 'center',
    },
    item: {
        cursor: 'pointer',
        borderBottom: "1px solid rgb(239, 243, 244)",
        '& .MuiListItem-root .MuiListItem-gutters': {
            padding: "0px 0px 0px 0px",
        },
        '& .MuiTypography-body1': {
            fontWeight: 700,
        },
        '& .MuiListItemAvatar-root': {
            minWidth: 50,
        },
        '&:hover': {
            backgroundColor: '#edf3f6',
        },
        '& a': {
            color: 'inherit',
            textDecoration: 'none',
        },
        "& svg": {
            color: "rgb(83, 100, 113)",
            marginBottom: 15,
            height: "1.2em",
        },
    },
}));
