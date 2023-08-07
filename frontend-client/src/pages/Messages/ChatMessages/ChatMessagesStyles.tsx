import { makeStyles, Theme } from "@material-ui/core";

export const useChatMessagesStyles = makeStyles((theme: Theme) => ({
    chatContainer: {
        minWidth: 600,
        padding: 0,
        borderLeft: 0
    },
    chat: {
        padding: "53px 15px",
        height: 900,
        overflowY: "auto",
        border: 0
    }
}));
