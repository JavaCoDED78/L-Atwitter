import React, { ChangeEvent, FormEvent, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory, Link } from "react-router-dom";
import { Button } from "@material-ui/core";
import TwitterIcon from "@material-ui/icons/Twitter";

import { LoginTextField } from "./LoginInputField";
import { useLoginStyles } from "./LoginStyles";
import { selectLoginErrorStatus } from "../../store/ducks/user/selectors";
import { fetchSignIn } from "../../store/ducks/user/actionCreators";

const Login = () => {
  const classes = useLoginStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const errorStatus = useSelector(selectLoginErrorStatus);
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const onSubmit = (event: FormEvent<HTMLFormElement>): void => {
    event.preventDefault();
    dispatch(fetchSignIn({ email, password, history }));
  };

  const handleChangeEmail = (event: ChangeEvent<HTMLInputElement>): void => {
    if (event.currentTarget) {
      setEmail(event.currentTarget.value);
    }
  };

  const handleChangePassword = (event: ChangeEvent<HTMLInputElement>): void => {
    if (event.currentTarget) {
      setPassword(event.currentTarget.value);
    }
  };

  return (
    <div className={classes.container}>
      <div>
        <TwitterIcon />
      </div>
      <h1>Log in to Twitter</h1>
      {errorStatus === 403 && (
        <div className={classes.error}>
          The username and password you entered did not match our records.
          Please double-check and try again.
        </div>
      )}
      <form onSubmit={onSubmit}>
        <div className={classes.input}>
          <LoginTextField
            label="Phone, email or username"
            type="email"
            variant="filled"
            onChange={handleChangeEmail}
            value={email}
          />
        </div>
        <div className={classes.input}>
          <LoginTextField
            label="Password"
            type="password"
            variant="filled"
            onChange={handleChangePassword}
            value={password}
          />
        </div>
        <Button
          className={classes.button}
          type="submit"
          variant="contained"
          color="primary"
          disabled={!(email && password)}
          fullWidth
        >
          Login
        </Button>
      </form>
      <div className={classes.footer}>
        <span>
          <Link to={"/account/forgot"}>Forgot password?</Link>
        </span>{" "}
        ·
        <span>
          <Link to={"/account/signin"}> Sign up for Twitter</Link>
        </span>
      </div>
    </div>
  );
};

export default Login;
