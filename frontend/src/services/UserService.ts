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
    const response = await api.post('/users/login', user);
    const userData = response.data;
    // Transform UserDto to User interface
    return {
        userId: userData.userId,
        username: userData.name, // Map name to username
        email: userData.email
    };
}

export const register = async (user: UserRegisterDto): Promise<User> => {
    const response = await api.post('/users/register', user);
    const userData = response.data;
    // Transform UserDto to User interface
    return {
        userId: userData.userId,
        username: userData.name, // Map name to username
        email: userData.email
    };
}

export const updatePassword = async (userId: number, newPassword: string): Promise<User> => {
    const response = await api.put(`/users/${userId}/password`, null, { params: { newPassword } });
    const userData = response.data;
    return {
        userId: userData.userId,
        username: userData.name,
        email: userData.email
    };
}

export const updateAddress = async (userId: number, address: string): Promise<User> => {
    const response = await api.put(`/users/${userId}/address`, null, { params: { address } });
    const userData = response.data;
    return {
        userId: userData.userId,
        username: userData.name,
        email: userData.email
    };
}
