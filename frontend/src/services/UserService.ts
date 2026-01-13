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
