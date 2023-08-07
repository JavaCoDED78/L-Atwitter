import React, { FC, FormEvent, ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Button, Dialog, DialogContent, DialogTitle, List, ListItem, Typography } from "@material-ui/core";

import { useListsModalStyles } from "./ListsModalStyles";
import {
    selectIsSimpleListsLoaded,
    selectIsSimpleListsLoading,
    selectSimpleListsItems
} from "../../store/ducks/lists/selectors";
import { fetchSimpleLists, processUserToLists, resetListsState } from "../../store/ducks/lists/actionCreators";
import CloseButton from "../CloseButton/CloseButton";
import { SimpleListResponse } from "../../types/lists";
import Spinner from "../Spinner/Spinner";
import ListsModalItem from "./ListsModalItem/ListsModalItem";

interface ListsModalProps {
    userId: number;
    visible?: boolean;
    onClose: () => void;
}

const ListsModal: FC<ListsModalProps> = ({ userId, visible, onClose }): ReactElement | null => {
    const classes = useListsModalStyles();
    const dispatch = useDispatch();
    const simpleLists = useSelector(selectSimpleListsItems);
    const isSimpleListsLoading = useSelector(selectIsSimpleListsLoading);
    const isSimpleListsLoaded = useSelector(selectIsSimpleListsLoaded);
    const [lists, setLists] = useState<SimpleListResponse[]>([]);

    useEffect(() => {
        if (visible) {
            dispatch(fetchSimpleLists(userId));
        }
    }, [visible]);

    useEffect(() => {
        setLists(simpleLists);
    }, [isSimpleListsLoaded]);

    const onSubmit = (event: FormEvent<HTMLFormElement>): void => {
        event.preventDefault();
        const listsRequest = lists.map((list) => {
            return {
                listId: list.id,
                isMemberInList: list.isMemberInList
            };
        });
        dispatch(processUserToLists({ userId, lists: listsRequest }));
        dispatch(resetListsState());
        setLists([]);
        onClose();
    };

    const onSelect = (listId: number): void => {
        const listsCopy = [...lists];
        const index = listsCopy.findIndex((list) => list.id === listId);
        listsCopy[index] = { ...listsCopy[index], isMemberInList: !listsCopy[index].isMemberInList };
        setLists(listsCopy);
    };

    if (!visible) {
        return null;
    }

    return (
        <Dialog open={visible} onClose={onClose} className={classes.dialog}>
            <form onSubmit={onSubmit}>
                <DialogTitle>
                    <CloseButton onClose={onClose} />
                    Pick a List
                    <Button type="submit" variant="contained" color="primary" size="small">
                        Save
                    </Button>
                </DialogTitle>
                <DialogContent className={classes.content}>
                    <Typography variant={"body1"} component={"div"} className={classes.createList}>
                        Create a new List
                    </Typography>
                    <div className={classes.list}>
                        {isSimpleListsLoading ? (
                            <Spinner />
                        ) : (
                            <List>
                                {lists.map((list) => (
                                    <ListItem
                                        key={list.id}
                                        onClick={() => onSelect(list.id)}
                                        selected={list.isMemberInList}
                                        dense
                                        button
                                    >
                                        <ListsModalItem list={list} />
                                    </ListItem>
                                ))}
                            </List>
                        )}
                    </div>
                </DialogContent>
            </form>
        </Dialog>
    );
};

export default ListsModal;
