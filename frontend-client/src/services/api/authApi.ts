import {AxiosResponse} from "axios";

import {axios} from "../../core/axios";
import {API_URL} from "../../util/url";
import {RegistrationInfo} from "../../pages/Authentication/Authentication";
import {RegistrationProps} from "../../pages/RegistrationModal/SetPasswordModal/SetPasswordModal";
import {LoginProps} from "../../pages/Login/Login";
import {AuthenticationResponse} from "../../store/types/auth";
import {AuthUserResponse} from "../../store/types/user";

export const AuthApi = {
    async signIn(postData: LoginProps): Promise<AxiosResponse<AuthenticationResponse>> {
        return await axios.post<AuthenticationResponse>(`${API_URL}/auth/login`, postData);
    },
    async checkEmail(postData: RegistrationInfo): Promise<AxiosResponse<string>> {
        return await axios.post<string>(`${API_URL}/auth/registration/check`, postData);
    },
    async sendRegistrationCode(postData: RegistrationInfo): Promise<AxiosResponse<string>> {
        return await axios.post<string>(`${API_URL}/auth/registration/code`, postData);
    },
    async checkRegistrationCode(registrationCode: string): Promise<AxiosResponse<string>> {
        return await axios.get<string>(`${API_URL}/auth/registration/activate/${registrationCode}`);
    },
    async endRegistration(postData: RegistrationProps): Promise<AxiosResponse<AuthenticationResponse>> {
        return await axios.post<AuthenticationResponse>(`${API_URL}/auth/registration/confirm`, postData);
    },
    async findExistingEmail(postData: { email: string }): Promise<AxiosResponse<string>> {
        return await axios.post<string>(`${API_URL}/auth/forgot/email`, postData);
    },
    async sendPasswordResetCode(postData: { email: string }): Promise<AxiosResponse<string>> {
        return await axios.post<string>(`${API_URL}/auth/forgot`, postData);
    },
    async getUserByResetCode(resetCode: string): Promise<AxiosResponse<AuthUserResponse>> {
        return await axios.get<AuthUserResponse>(`${API_URL}/auth/reset/${resetCode}`);
    },
    async passwordReset(postData: { email: string; password: string, password2: string }): Promise<AxiosResponse<string>> {
        return await axios.post<string>(`${API_URL}/auth/reset`, postData);
    },
    async currentPasswordReset(postData: { currentPassword: string; password: string, password2: string }): Promise<AxiosResponse<string>> {
        return await axios.post<string>(`${API_URL}/auth/reset/current`, postData);
    },
    async getMe(): Promise<AxiosResponse<AuthenticationResponse>> {
        return await axios.get<AuthenticationResponse>(`${API_URL}/auth/user`);
    },
};
