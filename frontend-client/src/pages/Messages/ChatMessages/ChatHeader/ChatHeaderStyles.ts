import { makeStyles, Theme } from "@material-ui/core";

export const useChatHeaderStyles = makeStyles((theme: Theme) => ({
    chatHeader: {
        width: 598
    },
    chatAvatar: {
        width: theme.spacing(4),
        height: theme.spacing(4),
        margin: "0px 15px"
    },
    iconGroup: {
        marginLeft: "auto",
        marginRight: 10
    }
}));
