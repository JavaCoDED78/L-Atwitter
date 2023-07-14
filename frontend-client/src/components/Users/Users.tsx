import React, {FC, ReactElement} from 'react';
import {Link, useLocation} from 'react-router-dom';
import {useSelector} from "react-redux";
import {CircularProgress, Paper} from "@material-ui/core";
import List from "@material-ui/core/List/List";
import ListItem from "@material-ui/core/ListItem/ListItem";

import {selectUsersIsLoading, selectUsers} from "../../store/ducks/users/selectors";
import UsersItem from "./UsersItem/UsersItem";
import {useUsersStyles} from "./UsersStyles";

const Users: FC = (): ReactElement => {
    const classes = useUsersStyles();
    const location = useLocation();
    const users = useSelector(selectUsers);
    const isUsersLoading = useSelector(selectUsersIsLoading);

    return (
        <>
            {(location.pathname !== "/home/connect") && (
                <Paper className={classes.container}>
                    <Paper className={classes.header} variant="outlined">
                        <b>Who to follow</b>
                    </Paper>
                    {isUsersLoading ? (
                        <div className={classes.loading}>
                            <CircularProgress/>
                        </div>
                    ) : (
                        <List>
                            {users.slice(0, 5).map((user) => <UsersItem key={user.id} item={user}/>)}
                            <Link to={"/home/connect"}>
                                <ListItem className={classes.footer}>
                                    Show more
                                </ListItem>
                            </Link>
                        </List>
                    )}
                </Paper>)
            }
        </>
    );
};

export default Users;
