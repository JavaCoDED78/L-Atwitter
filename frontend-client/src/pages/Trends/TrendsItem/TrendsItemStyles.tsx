import { makeStyles } from "@material-ui/core";

export const useTrendsItemStyles = makeStyles((theme) => ({
    item: {
        cursor: "pointer",
        borderBottom: `1px solid ${theme.palette.divider}`,
        "& .MuiListItem-root .MuiListItem-gutters": {
            padding: "0px 0px 0px 0px"
        },
        "& .MuiTypography-body1": {
            fontWeight: 700
        },
        "& .MuiListItemAvatar-root": {
            minWidth: 50
        },
        "&:hover": {
            backgroundColor: theme.palette.secondary.dark
        },
        "& a": {
            color: "inherit",
            textDecoration: "none"
        }
    }
}));
