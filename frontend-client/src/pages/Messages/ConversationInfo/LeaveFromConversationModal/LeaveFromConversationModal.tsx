import React, {FC, ReactElement} from 'react';
import {Button, Dialog, DialogContent, Typography} from "@material-ui/core";

import {useLeaveFromConversationModalStyles} from "./LeaveFromConversationModalStyles";

interface LeaveFromConversationModalProps {
    handleLeaveFromConversation: () => void;
    visible?: boolean;
    onClose: () => void;
}

const LeaveFromConversationModal: FC<LeaveFromConversationModalProps> = (
    {
        handleLeaveFromConversation,
        visible,
        onClose
    }
): ReactElement | null => {
    const classes = useLeaveFromConversationModalStyles();

    if (!visible) {
        return null;
    }

    return (
        <Dialog open={visible} onClose={onClose}>
            <DialogContent style={{padding: 0}}>
                <div className={classes.modalWrapper}>
                    <Typography component={"div"} className={classes.title}>
                        Leave conversation?
                    </Typography>
                    <Typography component={"div"} className={classes.text}>
                        This conversation will be deleted from your inbox. Other people in the conversation will still
                        be able to see it.
                    </Typography>
                    <Button
                        className={classes.blockButton}
                        onClick={handleLeaveFromConversation}
                        color="primary"
                        variant="contained"
                        fullWidth
                    >
                        Leave
                    </Button>
                    <Button
                        className={classes.cancelButton}
                        onClick={onClose}
                        color="primary"
                        variant="outlined"
                        fullWidth
                    >
                        Cancel
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default LeaveFromConversationModal;
