import { makeStyles, Theme } from "@material-ui/core";

export const useEmptyNotificationsStyles = makeStyles((theme: Theme) => ({
    infoWindow: {
        textAlign: "center",
        "& .MuiTypography-h4": {
            marginTop: 30
        }
    }
}));
