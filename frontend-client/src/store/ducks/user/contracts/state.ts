import {BackgroundTheme, ColorScheme, Image, LoadingStatus} from "../../../types/common";
import {AuthUserResponse} from "../../../types/user";

export interface UserState {
    data: AuthUserResponse | undefined;
    status: LoadingStatus;
}

export interface Settings {
    username?: string;
    email?: string;
    countryCode?: string;
    phone?: number;
    country?: string;
    gender?: string;
    language?: string;
    mutedDirectMessages?: boolean;
    privateProfile?: boolean;
    colorScheme?: ColorScheme;
    backgroundColor?: BackgroundTheme;
}

export interface UserRequest {
    username: string;
    location: string;
    website: string;
    avatar: Image;
    wallpaper: Image;
    about: string;
}

export interface UserActionRequest {
    userId: number;
    tweetId?: number;
}

export interface ChangePhoneResponse {
    countryCode: string;
    phone: number
}
