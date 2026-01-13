import api from './api';
import { Order } from '../types';

export const createOrder = async (userId: number): Promise<Order> => {
  try {
    const response = await api.post('/orders', null, { params: { userId } });
    return response.data;
  } catch (error) {
    console.error('Error creating order:', error);
    throw error;
  }
};

export const getOrdersByUser = async (userId: number): Promise<Order[]> => {
  try {
    const response = await api.get(`/orders/user/${userId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching orders:', error);
    throw error;
  }
};
