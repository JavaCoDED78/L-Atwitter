import React, { FC, ReactElement } from "react";
import FormGroup from "@material-ui/core/FormGroup";
import FormControl from "@material-ui/core/FormControl";
import TextField from "@material-ui/core/TextField";
import TwitterIcon from "@material-ui/icons/Twitter";
import SearchIcon from "@material-ui/icons/Search";
import PeopleOutlineIcon from "@material-ui/icons/PeopleOutline";
import ChatIcon from "@material-ui/icons/ChatBubbleOutlineOutlined";
import { Button, makeStyles, Typography } from "@material-ui/core";

import ModalBlock from "../../components/ModalBlock/ModalBlock";

export const useStylesSignIn = makeStyles((theme) => ({
  wrapper: {
    display: "flex",
    height: "100vh",
  },
  leftSide: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    flex: "0 0 50%",
    backgroundColor: "rgb(12,176,132)",
    overflow: "hidden",
    position: "relative",
  },
  leftSideListInfo: {
    position: "relative",
    listStyle: "none",
    padding: 0,
    margin: 0,
    width: 380,
    "& h6": {
      display: "flex",
      alignItems: "center",
      color: "white",
      fontWeight: 700,
      fontSize: 20,
    },
  },
  leftSideListInfoItem: {
    marginBottom: 40,
  },
  leftSideIcon: {
    fontSize: 32,
    marginRight: 15,
  },
  leftSideTwitterIcon: {
    position: "absolute",
    left: "50%",
    top: "50%",
    transform: "translate(-50%, -50%)",
    width: "180%",
    height: "180%",
  },
  rightSide: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    flex: "0 0 50%",
  },
  rightSideWrapper: {
    width: 380,
  },
  rightSideTwitterIcon: {
    marginLeft: 10,
    fontSize: 45,
  },
  rightSideTittle: {
    fontWeight: 700,
    fontSize: 32,
    marginBottom: 45,
    marginTop: 20,
  },
  loginSideField: {
    marginBottom: 18,
  },
  registerField: {
    marginBottom: theme.spacing(5),
  },
  loginFormControl: {
    marginBottom: theme.spacing(2),
  },
}));

const SignIn: FC = (): ReactElement => {
  const classes = useStylesSignIn();
  const [visibleModal, setVisibleModal] = React.useState<"signIn" | "signUp">();

  const handleClickOpenSignIn = (): void => {
    setVisibleModal("signIn");
  };

  const handleClickOpenSignUp = (): void => {
    setVisibleModal("signUp");
  };

  const handleCloseModal = (): void => {
    setVisibleModal(undefined);
  };

  return (
    <div className={classes.wrapper}>
      <section className={classes.leftSide}>
        <TwitterIcon color="primary" className={classes.leftSideTwitterIcon} />
        <ul className={classes.leftSideListInfo}>
          <li className={classes.leftSideListInfoItem}>
            <Typography variant="h6">
              <SearchIcon className={classes.leftSideIcon} />
              Read about what interests you.
            </Typography>
          </li>
          <li className={classes.leftSideListInfoItem}>
            <Typography variant="h6">
              <PeopleOutlineIcon className={classes.leftSideIcon} />
              Find out what the world is talking about.
            </Typography>
          </li>
          <li className={classes.leftSideListInfoItem}>
            <Typography variant="h6">
              <ChatIcon className={classes.leftSideIcon} />
              Join the conversation.
            </Typography>
          </li>
        </ul>
      </section>
      <section className={classes.rightSide}>
        <div className={classes.rightSideWrapper}>
          <TwitterIcon
            color="primary"
            className={classes.rightSideTwitterIcon}
          />
          <Typography className={classes.rightSideTittle} variant="h4">
            Find out what's happening in the world right now
          </Typography>
          <Typography>
            <b>Join Twitter now!</b>
          </Typography>
          <br />
          <Button
            onClick={handleClickOpenSignUp}
            style={{ marginBottom: "20px" }}
            variant="contained"
            color="primary"
            fullWidth
          >
            Register
          </Button>
          <Button
            onClick={handleClickOpenSignIn}
            variant="outlined"
            color="primary"
            fullWidth
          >
            Log In
          </Button>
          <ModalBlock
            visible={visibleModal === "signIn"}
            onClose={handleCloseModal}
            classes={classes}
            title="Sign In"
          >
            <FormControl
              className={classes.loginFormControl}
              component="fieldset"
              fullWidth
            >
              <FormGroup aria-label="position" row>
                <TextField
                  className={classes.loginSideField}
                  id="email"
                  label="E-Mail"
                  variant="outlined"
                  type="email"
                  fullWidth
                />
                <TextField
                  className={classes.loginSideField}
                  id="password"
                  label="Password"
                  variant="outlined"
                  type="password"
                  fullWidth
                />
                <Button
                  onClick={handleCloseModal}
                  variant="contained"
                  color="primary"
                  fullWidth
                >
                  Log In
                </Button>
              </FormGroup>
            </FormControl>
          </ModalBlock>
          <ModalBlock
            visible={visibleModal === "signUp"}
            onClose={handleCloseModal}
            classes={classes}
            title="Create acount"
          >
            <FormControl
              className={classes.loginFormControl}
              component="fieldset"
              fullWidth
            >
              <FormGroup aria-label="position" row>
                <TextField
                  className={classes.registerField}
                  id="name"
                  label="Name"
                  variant="outlined"
                  type="name"
                  fullWidth
                />
                <TextField
                  className={classes.registerField}
                  id="email"
                  label="E-Mail"
                  variant="outlined"
                  type="email"
                  fullWidth
                />
                <TextField
                  className={classes.registerField}
                  id="password"
                  label="Password"
                  variant="outlined"
                  type="password"
                  fullWidth
                />
                <Button variant="contained" color="primary" fullWidth>
                  Register
                </Button>
              </FormGroup>
            </FormControl>
          </ModalBlock>
        </div>
      </section>
    </div>
  );
};

export default SignIn;
