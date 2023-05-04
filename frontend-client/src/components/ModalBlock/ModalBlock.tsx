import React, { FC } from "react";
import { useStylesSignIn } from "../../pages/SignIn/SignIn";
import { Dialog, DialogContent, DialogTitle } from "@material-ui/core";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";

interface ModalBlockProps {
  title?: string;
  children: React.ReactNode;
  classes?: ReturnType<typeof useStylesSignIn>;
  visible?: boolean;
  onClose: () => void;
}

const ModalBlock: FC<ModalBlockProps> = ({
  title,
  onClose,
  visible = false,
  children,
}: ModalBlockProps): React.ReactElement | null => {
  if (!visible) {
    return null;
  }

  return (
    <Dialog
      open={visible}
      onClose={onClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle id="form-dialog-title">
        <IconButton onClick={onClose} color="secondary" aria-label="close">
          <CloseIcon style={{ fontSize: 26 }} color="secondary" />
        </IconButton>
        {title}
      </DialogTitle>
      <DialogContent>{children}</DialogContent>
    </Dialog>
  );
};

export default ModalBlock;
