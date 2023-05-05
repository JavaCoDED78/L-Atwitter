import { FC, ReactElement } from "react";
import { useHomeStyles } from "../../pages/Home/HomeStyles";
import { useSelector } from "react-redux";
import { selectUsersItems } from "../../store/actions/users/selectors";
import Paper from "@material-ui/core/Paper";
import {
  Button,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Typography,
} from "@material-ui/core";
import Avatar from "@material-ui/core/Avatar";
import PersonAddIcon from "@material-ui/icons/PersonAddOutlined";

const Users: FC = (): ReactElement => {
  const classes = useHomeStyles();
  const items = useSelector(selectUsersItems);

  return (
    <Paper className={classes.rightSideBlock}>
      <Paper className={classes.rightSideBlockHeader} variant="outlined">
        <b>Кого читать</b>
      </Paper>
      <List>
        {items.map(() => {
          return (
            <ListItem className={classes.rightSideBlockItem}>
              <ListItemAvatar>
                <Avatar
                  alt="Remy Sharp"
                  src="https://avatars.githubusercontent.com/u/56604599?v=4"
                />
              </ListItemAvatar>
              <ListItemText
                primary="Dock Of Shame"
                secondary={
                  <Typography
                    component="span"
                    variant="body2"
                    color="textSecondary"
                  >
                    @FavDockOfShame
                  </Typography>
                }
              />
              <Button color="primary">
                <PersonAddIcon />
              </Button>
            </ListItem>
          );
        })}
        <Divider component="li" />
      </List>
    </Paper>
  );
};

export default Users;
