import React, { FC, ReactElement } from "react";
import { useDispatch } from "react-redux";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import Dialog from "@material-ui/core/Dialog";
import CloseIcon from "@material-ui/icons/Close";
import IconButton from "@material-ui/core/IconButton";

import { User } from "../../store/ducks/user/contracts/state";
import Follower from "../Follower/Follower";
import {
  followUser,
  unfollowUser,
} from "../../store/ducks/user/actionCreators";
import { LikeTweet, Retweet } from "../../store/ducks/tweets/contracts/state";

interface UsersListModalProps {
  users?: LikeTweet[] | Retweet[];
  visible?: boolean;
  title: string;
  onClose: () => void;
}

const UsersListModal: FC<UsersListModalProps> = ({
  users,
  visible,
  title,
  onClose,
}): ReactElement | null => {
  const dispatch = useDispatch();

  const handleFollow = (user: User): void => {
    dispatch(followUser(user));
  };

  const handleUnfollow = (user: User): void => {
    dispatch(unfollowUser(user));
  };

  if (!visible) {
    return null;
  }

  return (
    <Dialog
      open={visible}
      onClose={onClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle id="form-dialog-title" style={{ margin: 0 }}>
        <IconButton onClick={onClose} color="secondary" aria-label="close">
          <CloseIcon style={{ fontSize: 26 }} color="secondary" />
        </IconButton>
        {title}
      </DialogTitle>
      <DialogContent style={{ height: 550, width: 598, padding: 0 }}>
        {users?.map((user) => (
          <Follower
            item={user.user}
            follow={handleFollow}
            unfollow={handleUnfollow}
          />
        ))}
      </DialogContent>
    </Dialog>
  );
};

export default UsersListModal;
