import { User, UserCreateDto } from "../types";
import api from "./api";


export const login = async (user: UserCreateDto): Promise<User> => {
    const response = await api.post<User>('/users/login', user);
    return response.data;
}

export const register = async (user: UserCreateDto): Promise<User> => {
    const response = await api.post<User>('/users/register', user);
    return response.data;
}