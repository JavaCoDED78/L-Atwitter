import { makeStyles, Theme } from "@material-ui/core";

export const useManageContactsStyles = makeStyles((theme: Theme) => ({
    removeContacts: {
        textAlign: "center",
        padding: 16,
        cursor: "pointer",
        "& .MuiTypography-body1": {
            color: theme.palette.error.main
        },
        "&:hover": {
            backgroundColor: "rgba(244, 33, 46, 0.1)"
        }
    }
}));
