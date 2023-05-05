import React, { FC, ReactElement, useEffect } from "react";
import { Route, Switch, useHistory } from "react-router-dom";

import SignIn from "./pages/SignIn/SignIn";
import Home from "./pages/Home/Home";
import { User } from "./pages/User/User";
import { Layout } from "./pages/Layout";
import TwitterIcon from "@material-ui/icons/Twitter";
import { useHomeStyles } from "./pages/Home/HomeStyles";
import { useDispatch, useSelector } from "react-redux";
import { LoadingStatus } from "./store/types";
import { fetchUserData } from "./store/actions/user/actionCreators";
import { selectIsAuth, selectUserStatus } from "./store/actions/user/selectors";

const App: FC = (): ReactElement => {
  const classes = useHomeStyles();
  const history = useHistory();
  const dispatch = useDispatch();
  const isAuth = useSelector(selectIsAuth);
  const loadingStatus = useSelector(selectUserStatus);
  const isReady =
    loadingStatus !== LoadingStatus.NEVER &&
    loadingStatus !== LoadingStatus.LOADING;

  useEffect(() => {
    dispatch(fetchUserData());
  }, [dispatch]);

  useEffect(() => {
    if (!isAuth && isReady) {
      history.push("/signin");
    } else {
      history.push("/home");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isAuth, isReady]);

  if (!isReady) {
    return (
      <div className={classes.centered}>
        <TwitterIcon color="primary" style={{ width: 80, height: 80 }} />
      </div>
    );
  }
  return (
    <div className="App">
      <Switch>
        <Route path="/signin" component={SignIn} exact />
        <Layout>
          <Route path="/home" component={Home} />
          <Route path="/user" component={User} />
        </Layout>
      </Switch>
    </div>
  );
};

export default App;
