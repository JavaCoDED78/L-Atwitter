import React, { FC, ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Divider, Typography } from "@material-ui/core";

import { useChangePhoneStyles } from "./ChangePhoneStyles";
import { ChangeInfoTextField } from "../../../ChangeInfoTextField/ChangeInfoTextField";
import ChangePhoneModal from "./ChangePhoneModal/ChangePhoneModal";
import {
    selectUserIsSuccess,
    selectUserProfileCountryCode,
    selectUserProfilePhone
} from "../../../../../store/ducks/user/selectors";
import { getPhoneCode } from "../../../../../util/country-code-helper";
import { setUserLoadingStatus } from "../../../../../store/ducks/user/actionCreators";
import { withDocumentTitle } from "../../../../../hoc/withDocumentTitle";
import { LoadingStatus } from "../../../../../types/common";

const ChangePhone: FC = (): ReactElement => {
    const classes = useChangePhoneStyles();
    const dispatch = useDispatch();
    const countryCode = useSelector(selectUserProfileCountryCode);
    const phone = useSelector(selectUserProfilePhone);
    const isUpdatedSuccess = useSelector(selectUserIsSuccess);
    const [visibleChangePhoneModal, setVisibleChangePhoneModal] = useState<boolean>(false);

    useEffect(() => {
        setVisibleChangePhoneModal(false);
        dispatch(setUserLoadingStatus(LoadingStatus.NEVER));
    }, [isUpdatedSuccess]);

    const onOpenChangePhoneModal = (): void => {
        setVisibleChangePhoneModal(true);
    };

    const onCloseChangePhoneModal = (): void => {
        setVisibleChangePhoneModal(false);
    };

    return (
        <>
            <div className={classes.textFieldWrapper}>
                <ChangeInfoTextField
                    label="Current"
                    type="text"
                    variant="filled"
                    value={`${getPhoneCode(countryCode)}${phone}`}
                    fullWidth
                    disabled
                />
            </div>
            <Divider />
            <div
                id={"openChangePhoneModal"}
                className={classes.updatePhoneNumber}
                onClick={onOpenChangePhoneModal}
            >
                <Typography variant={"body1"} component={"span"}>
                    Update phone number
                </Typography>
            </div>
            <div className={classes.deletePhoneNumber}>
                <Typography variant={"body1"} component={"span"}>
                    Delete phone number
                </Typography>
            </div>
            {visibleChangePhoneModal &&
                <ChangePhoneModal visible={visibleChangePhoneModal} onClose={onCloseChangePhoneModal} />}
        </>
    );
};

export default withDocumentTitle(ChangePhone)("Change phone");
