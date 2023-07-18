import React, {FC, ReactElement, useState} from 'react';
import TwitterIcon from "@material-ui/icons/Twitter";
import {Button, Dialog, DialogContent, Link as MuiLink, Typography} from "@material-ui/core";

import {useEmailVerificationModalStyles} from "./EmailVerificationModalStyles";
import {RegistrationInputField} from "../RegistrationInput/RegistrationInputField";
import {AuthApi} from "../../../services/api/authApi";

interface CustomizeModalProps {
    email: string;
    open: boolean;
    onClose: () => void;
    onOpenSetPassword: (value: boolean | ((prevVar: boolean) => boolean)) => void;
}

const EmailVerificationModal: FC<CustomizeModalProps> = ({email, open, onClose, onOpenSetPassword}): ReactElement => {
    const classes = useEmailVerificationModalStyles()
    const [verificationCode, setVerificationCode] = useState<string>("");
    const [error, setError] = useState<string>("");

    const checkEmailVerificationCode = (): void => {
        AuthApi.checkRegistrationCode(verificationCode)
            .then((response) => onOpenSetPassword(true))
            .catch((error) => setError(error.response.data));
    };

    return (
        <Dialog
            hideBackdrop={true}
            transitionDuration={0}
            open={open}
            onClose={onClose}
            aria-labelledby="form-dialog-title"
        >
            <DialogContent style={{paddingTop: 0, paddingBottom: 0}} className={classes.container}>
                <div className={classes.logoIcon}>
                    <TwitterIcon/>
                </div>
                <Typography variant={"h3"} component={"div"}>
                    We sent you a code
                </Typography>
                <Typography variant={"subtitle1"} component={"div"}>
                    {`Enter it below to verify ${email}.`}
                </Typography>
                <div style={{marginTop: 10}}>
                    <RegistrationInputField
                        label="Verification code"
                        variant="filled"
                        helperText={error}
                        error={error !== ""}
                        value={verificationCode}
                        onChange={(event) => setVerificationCode(event.target.value)}
                        fullWidth
                    />
                </div>
                <MuiLink variant="subtitle2" href="#" className={classes.emailLinkWrapper}>
                    Didn't receive email?
                </MuiLink>
                <div className={classes.buttonWrapper}>
                    <Button
                        disabled={verificationCode === ""}
                        onClick={checkEmailVerificationCode}
                        variant="contained"
                        color="primary"
                        size="small"
                        fullWidth
                    >
                        Next
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default EmailVerificationModal;
