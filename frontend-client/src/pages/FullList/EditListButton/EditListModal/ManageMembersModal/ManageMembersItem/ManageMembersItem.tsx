import React, {FC, memo, ReactElement, useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import {Avatar, Button, Paper, Typography} from "@material-ui/core";

import {useManageMembersItemStyles} from "./ManageMembersItemStyles";
import {DEFAULT_PROFILE_IMG} from "../../../../../../util/url";
import {selectUserData} from "../../../../../../store/ducks/user/selectors";
import {useGlobalStyles} from "../../../../../../util/globalClasses";
import {ListsOwnerMemberResponse} from "../../../../../../store/types/lists";
import {processUserToListMembers} from "../../../../../../store/ducks/listMembers/actionCreators";
import PopperUserWindow from "../../../../../../components/PopperUserWindow/PopperUserWindow";
import {LockIcon} from "../../../../../../icons";
import ActionSnackbar from "../../../../../../components/ActionSnackbar/ActionSnackbar";
import {selectIsListSuggestedError} from "../../../../../../store/ducks/listMembers/selectors";
import {PROFILE} from "../../../../../../util/pathConstants";
import {useHoverUser} from "../../../../../../hook/useHoverUser";
import {useSnackbar} from "../../../../../../hook/useSnackbar";

interface ManageMembersItemProps {
    listId?: number
    listOwnerId?: number
    user?: ListsOwnerMemberResponse;
    isSuggested?: boolean;
}

const ManageMembersItem: FC<ManageMembersItemProps> = memo((
    {
        listId,
        listOwnerId,
        user,
        isSuggested,
    }
): ReactElement => {
    const globalClasses = useGlobalStyles();
    const classes = useManageMembersItemStyles();
    const dispatch = useDispatch();
    const myProfile = useSelector(selectUserData);
    const isSuggestedError = useSelector(selectIsListSuggestedError);
    const {visiblePopperWindow, handleHoverPopper, handleLeavePopper} = useHoverUser();
    const {openSnackBar, setOpenSnackBar, onCloseSnackBar} = useSnackbar();

    useEffect(() => {
        if (isSuggestedError) {
            setOpenSnackBar(true);
        }
    }, [isSuggestedError]);

    const onClickAddUserToList = (event: React.MouseEvent<HTMLButtonElement>): void => {
        event.preventDefault();
        dispatch(processUserToListMembers({userId: user?.id!, listId: listId!, isSuggested}));
    }

    return (
        <Link to={`${PROFILE}/${user?.id}`} className={globalClasses.link}>
            <Paper className={classes.container} variant="outlined">
                <Avatar
                    className={classes.listAvatar}
                    src={user?.avatar?.src ? user?.avatar.src : DEFAULT_PROFILE_IMG}
                />
                <div style={{flex: 1}}>
                    <div className={classes.header}>
                        <div onMouseLeave={handleLeavePopper} className={classes.headerUserInfo}>
                            <Typography
                                id={"fullName"}
                                variant={"h6"}
                                component={"span"}
                                onMouseEnter={() => handleHoverPopper(user?.id!)}
                            >
                                {user?.fullName}
                            </Typography>
                            {user?.isPrivateProfile && (
                                <span className={classes.lockIcon}>
                                    {LockIcon}
                                </span>
                            )}
                            <PopperUserWindow visible={visiblePopperWindow}/>
                            <Typography variant={"subtitle1"} component={"div"}>
                                @{user?.username}
                            </Typography>
                            <Typography variant={"body1"} component={"div"}>
                                {user?.about}
                            </Typography>
                        </div>
                        <div className={classes.buttonWrapper}>
                            {(listOwnerId === myProfile?.id) && (
                                (user?.id === myProfile?.id) ? null : (
                                    <Button
                                        className={classes[user?.isMemberInList ? "containedButton" : "outlinedButton"]}
                                        onClick={onClickAddUserToList}
                                        variant={user?.isMemberInList ? "contained" : "outlined"}
                                        color="primary"
                                        size="small"
                                    >
                                        {user?.isMemberInList ? "Remove" : "Add"}
                                    </Button>
                                )
                            )}
                        </div>
                    </div>
                </div>
                <ActionSnackbar
                    snackBarMessage={"You aren’t allowed to add this member to this List."}
                    openSnackBar={openSnackBar}
                    onCloseSnackBar={onCloseSnackBar}
                />
            </Paper>
        </Link>
    );
});

export default ManageMembersItem;
