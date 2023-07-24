import {makeStyles, Theme} from "@material-ui/core";
import ReplyIconButton from "./ReplyIconButton";

export const useReplyIconButtonStyles = makeStyles((theme: Theme) => ({
    infoIcon: {
        "& .MuiIconButton-root": {
            "& svg": {
                color: theme.palette.text.secondary,
                width: "1.406rem",
                height: "1.406rem",
            },
        },
    },
}));
