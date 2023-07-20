import React, {FC, FormEvent, ReactElement, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Button, Dialog, Divider, InputAdornment, List, ListItem} from "@material-ui/core";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";

import {useMessagesModalStyles} from "./MessagesModalStyles";
import {MessagesModalInput} from "./MessagesModalInput/MessagesModalInput"
import {fetchUsersSearchByUsername, setUsersSearch} from "../../../store/ducks/usersSearch/actionCreators";
import {selectUsersSearch} from "../../../store/ducks/usersSearch/selectors";
import MessagesModalUser from './MessagesModalUser/MessagesModalUser';
import {createChat} from "../../../store/ducks/chats/actionCreators";
import {SearchIcon} from "../../../icons";
import CloseButton from "../../../components/CloseButton/CloseButton";
import {selectUserData} from "../../../store/ducks/user/selectors";
import {UserResponse} from "../../../store/types/user";

interface MessagesModalProps {
    visible?: boolean;
    onClose: () => void;
}

const MessagesModal: FC<MessagesModalProps> = ({visible, onClose}): ReactElement | null => {
    const classes = useMessagesModalStyles();
    const dispatch = useDispatch();
    const users = useSelector(selectUsersSearch);
    const myProfile = useSelector(selectUserData);
    const [text, setText] = useState<string>("");
    const [selectedIndex, setSelectedIndex] = useState<number>();
    const [selectedUser, setSelectedUser] = useState<UserResponse>();

    const handleClickSearch = (event: FormEvent<HTMLFormElement>): void => {
        event.preventDefault();
        dispatch(fetchUsersSearchByUsername(encodeURIComponent(text)));
    };

    const onSearch = (text: string): void => {
        if (text) {
            setText(text);
            dispatch(fetchUsersSearchByUsername(encodeURIComponent(text)));
        } else {
            setText("");
            dispatch(setUsersSearch([]));
        }
    };

    const handleClickAddUserToChat = (): void => {
        dispatch(createChat(selectedUser?.id!));
        dispatch(setUsersSearch([]));
        onClose();
    };

    const handleListItemClick = (user: UserResponse): void => {
        if (user.isMutedDirectMessages) {
            if (user.id !== selectedIndex) {
                setSelectedIndex(user.id);
                setSelectedUser(user);
            } else {
                setSelectedIndex(undefined);
                setSelectedUser(undefined);
            }
        }
    };

    if (!visible) {
        return null;
    }

    return (
        <Dialog open={visible} onClose={onClose} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title" className={classes.header}>
                <CloseButton onClose={onClose}/>
                New message
                <Button
                    onClick={handleClickAddUserToChat}
                    className={classes.button}
                    type="submit"
                    variant="contained"
                    color="primary"
                    size="small"
                    disabled={selectedIndex === undefined}
                >
                    Next
                </Button>
            </DialogTitle>
            <DialogContent className={classes.content}>
                <form onSubmit={handleClickSearch}>
                    <MessagesModalInput
                        fullWidth
                        placeholder="Explore people"
                        variant="outlined"
                        onChange={(event) => onSearch(event.target.value)}
                        value={text}
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                    {SearchIcon}
                                </InputAdornment>
                            ),
                        }}
                    />
                </form>
                <Divider/>
                <List component="nav" aria-label="main mailbox folders">
                    {users.map((user) => (
                        <ListItem
                            key={user.id}
                            button
                            selected={selectedIndex === user.id!}
                            disabled={user.isMutedDirectMessages || user.id === myProfile?.id}
                            onClick={() => handleListItemClick(user)}
                        >
                            <MessagesModalUser user={user}/>
                        </ListItem>
                    ))}
                </List>
            </DialogContent>
        </Dialog>
    );
};

export default MessagesModal;
