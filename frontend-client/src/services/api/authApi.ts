import {axios} from "../../core/axios";
import {LoginFormProps} from "../../pages/SignIn/LoginModal";
import {AuthUser, User} from "../../store/ducks/user/contracts/state";
import {RegisterFormProps} from "../../pages/SignIn/RegisterModal";

export interface Response<T> {
    status: string;
    data: T;
}

export const AuthApi = {
    async verify(hash: string): Promise<Response<any>> {
        const {data} = await axios.get<Response<any>>('/auth/verify?hash=' + hash);
        return data;
    },
    async signIn(postData: LoginFormProps): Promise<Response<AuthUser>> {
        const data = await axios.post<Response<AuthUser>>('http://localhost:8080/api/v1/auth/login', postData);
        return data.data;
    },
    async signUp(postData: RegisterFormProps): Promise<Response<AuthUser>> {
        const data = await axios.post<Response<AuthUser>>('http://localhost:8080/api/v1/auth/registration', postData);
        return data.data;
    },
    async getMe(): Promise<AuthUser> {
        const {data} = await axios.get<AuthUser>('http://localhost:8080/api/v1/auth/user');
        return data;
    },
    async getUsers(): Promise<User[]> {
        const {data} = await axios.get<User[]>('http://localhost:8080/api/v1/user/all');
        return data;
    },
    async getRelevantUsers(): Promise<User[]> {
        const {data} = await axios.get<User[]>('http://localhost:8080/api/v1/user/relevant');
        return data;
    },
    async getUserFollowers(userId: string): Promise<User[] | undefined> {
        const {data} = await axios.get<User[] | undefined>(`http://localhost:8080/api/v1/user/${userId}/followers`);
        return data;
    },
    async getUserFollowing(userId: string): Promise<User[] | undefined> {
        const {data} = await axios.get<User[] | undefined>(`http://localhost:8080/api/v1/user/${userId}/following`);
        return data;
    },
    async searchUsersByUsername(name: string): Promise<User[] | undefined> {
        const {data} = await axios.get<User[] | undefined>(`http://localhost:8080/api/v1/user/search/${name}`);
        return data;
    },
    async getUserInfo(userId: string): Promise<User | undefined> {
        const {data} = await axios.get<User | undefined>('http://localhost:8080/api/v1/user/' + userId);
        return data;
    },
    async updateUserProfile(userData: User): Promise<User> {
        const {data} = await axios.put<User>('http://localhost:8080/api/v1/user', userData);
        return data;
    },
    async follow(user: User): Promise<User | undefined> {
        const {data} = await axios.get<User | undefined>('http://localhost:8080/api/v1/user/follow/' + user.id);
        return data;
    },
    async unfollow(user: User): Promise<User | undefined> {
        const {data} = await axios.get<User | undefined>('http://localhost:8080/api/v1/user/unfollow/' + user.id);
        return data;
    },
};
