import React, {FormEvent, useState} from 'react';
import {Button} from "@material-ui/core";
import {Link} from 'react-router-dom';

import {ForgotPasswordTextField} from "../ForgotPasswordTextField/ForgotPasswordTextField";
import {useForgotPasswordStyles} from "../ForgotPasswordStyles";

const CheckEmailCode = () => {
    const classes = useForgotPasswordStyles();
    const [resetCode, setResetCode] = useState<string>("");
    const [error, setError] = useState<boolean>(false);

    const verifyResetCode = (event: FormEvent<HTMLFormElement>): void => {
        event.preventDefault();

        if (resetCode === "") {
            setError(true);
        } else {
            setError(false);
        }
    };

    return (
        <>
            <h1>Check your email</h1>
            <p>You'll receive a code to verify here so you can reset your account password.</p>
            <form onSubmit={verifyResetCode}>
                <ForgotPasswordTextField
                    error={error}
                    placeholder="Enter your code"
                    variant="outlined"
                    onChange={(event) => setResetCode(event.target.value)}
                    value={resetCode}
                />
                {error && <div className={classes.errorMessage}>Incorrect code. Please try again.</div>}
                <Button
                    className={classes.button}
                    type="submit"
                    variant="contained"
                    color="primary"
                >
                    Verify
                </Button>
            </form>
            <div>
                <p className={classes.footerText}>
                    If you don't see the email, check other places it might be, like your junk, spam, social,
                    or other folders.
                </p>
                <p className={classes.link}>Didn’t receive your code?</p>
            </div>
        </>
    );
};

export default CheckEmailCode;
