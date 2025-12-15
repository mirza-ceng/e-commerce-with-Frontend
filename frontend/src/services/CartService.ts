import api from './api';
import { CartItem } from '../types';

export const addToCart = async (productId: number, userId: number, quantity: number): Promise<CartItem> => {
  try {
    const params = new URLSearchParams();
    params.append('productId', String(productId));
    params.append('userId', String(userId));
    params.append('quantity', String(quantity));

    const response = await api.post('/cartItems', params);
    return response.data;
  } catch (error: any) {
    if (error.response && error.response.data && error.response.data.message) {
      throw new Error(error.response.data.message);
    } else {
      throw new Error('Failed to add product to cart. Please try again.');
    }
  }
};
