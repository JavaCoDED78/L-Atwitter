import {RootState} from '../../store';
import {UserDetailState} from './contracts/state';
import {DEFAULT_PROFILE_IMG} from "../../../util/url";
import {LoadingStatus} from "../../types/common";

export const selectUserDetail = (state: RootState): UserDetailState => state.userDetail;
export const selectUserDetailItem = (state: RootState) => selectUserDetail(state).item;
export const selectUserDetailId = (state: RootState) => selectUserDetailItem(state)?.id;
export const selectUserDetailUsername = (state: RootState) => selectUserDetailItem(state)?.username;
export const selectUserDetailFullName = (state: RootState) => selectUserDetailItem(state)?.fullName;
export const selectUserDetailAbout = (state: RootState) => selectUserDetailItem(state)?.about;
export const selectUserDetailFollowersSize = (state: RootState) => selectUserDetailItem(state)?.followersSize;
export const selectUserDetailFollowingSize = (state: RootState) => selectUserDetailItem(state)?.followingSize;
export const selectUserDetailAvatar = (state: RootState) => {
    return selectUserDetailItem(state)!.avatar ? selectUserDetailItem(state)!.avatar.src : DEFAULT_PROFILE_IMG
};
export const selectUserDetailIsMyProfileBlocked = (state: RootState) => selectUserDetailItem(state)?.isMyProfileBlocked;
export const selectUserDetailIsFollower = (state: RootState) => selectUserDetailItem(state)?.isFollower;
export const selectUserDetailIsUserBlocked = (state: RootState) => selectUserDetailItem(state)?.isUserBlocked;
export const selectUserDetailIsWaitingForApprove = (state: RootState) => selectUserDetailItem(state)?.isWaitingForApprove;
export const selectUserDetailIsPrivateProfile = (state: RootState) => selectUserDetailItem(state)?.isPrivateProfile;
export const selectUserDetailSameFollowers = (state: RootState) => selectUserDetailItem(state)?.sameFollowers;
export const selectLoadingState = (state: RootState): LoadingStatus => selectUserDetail(state).loadingState;
export const selectIsUserDetailLoading = (state: RootState): boolean => selectLoadingState(state) === LoadingStatus.LOADING;
export const selectIsUserDetailLoaded = (state: RootState): boolean => selectLoadingState(state) === LoadingStatus.LOADED;
