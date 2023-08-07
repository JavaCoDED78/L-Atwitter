import { makeStyles, Theme } from "@material-ui/core";

export const useNotificationAuthorItemStyles = makeStyles((theme: Theme) => ({
    notificationAvatarWrapper: {
        display: "inline-block"
    },
    notificationAvatar: {
        display: "inline-block",
        marginRight: 8,
        width: theme.spacing(4),
        height: theme.spacing(4),
        marginBottom: 11
    }
}));
