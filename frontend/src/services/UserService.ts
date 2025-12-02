import api from "./api";
import { User } from "../types";

interface UserLogin {
    email: string;
    password: string;
}

interface UserRegisterDto {
    email: string;
    password: string;
    username: string;
}
export const login = async (user: UserLogin): Promise<User> => {
    const response = await api.post<User>('/users/login', user);
    return response.data;
}

export const register = async (user: UserRegisterDto): Promise<User> => {
    const response = await api.post<User>('/users/register', user);
    return response.data;
}