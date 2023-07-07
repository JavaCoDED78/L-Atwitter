import { axios } from "../../core/axios";
import { LoginFormProps } from "../../pages/SignIn/LoginModal";
import { AuthUser, User } from "../../store/ducks/user/contracts/state";
import { RegisterFormProps } from "../../pages/SignIn/RegisterModal";
import { API_URL } from "../../util/url";
import { AxiosResponse } from "axios";

export interface Response<T> {
  status: string;
  data: T;
}

export const AuthApi = {
  async verify(hash: string): Promise<Response<any>> {
    const { data } = await axios.get<Response<any>>(
      "/auth/verify?hash=" + hash
    );
    return data;
  },
  async signIn(postData: LoginFormProps): Promise<Response<AuthUser>> {
    const data = await axios.post<Response<AuthUser>>(
      API_URL + "/auth/login",
      postData
    );
    return data.data;
  },
  async signUp(postData: RegisterFormProps): Promise<Response<AuthUser>> {
    const data = await axios.post<Response<AuthUser>>(
      API_URL + "/auth/registration",
      postData
    );
    return data.data;
  },
  async findExistingEmail(postData: {
    email: string;
  }): Promise<AxiosResponse<string>> {
    return await axios.post<string>(API_URL + "/auth/forgot/email", postData);
  },
  async sendPasswordResetCode(postData: {
    email: string;
  }): Promise<AxiosResponse<string>> {
    return await axios.post<string>(API_URL + "/auth/forgot", postData);
  },
  async getUserByResetCode(resetCode: string): Promise<User> {
    const data = await axios.get<User>(API_URL + "/auth/reset/" + resetCode);
    return data.data;
  },
  async passwordReset(postData: {
    email: string;
    password: string;
    password2: string;
  }): Promise<string> {
    const data = await axios.post<string>(API_URL + "/auth/reset", postData);
    return data.data;
  },
  async getMe(): Promise<AuthUser> {
    const { data } = await axios.get<AuthUser>(API_URL + "/auth/user");
    return data;
  },
};
