import api from './api';
import { CartItem } from '../types';

export const addToCart = async (productId: number, userId: number, quantity: number): Promise<CartItem> => {
  const params = new URLSearchParams();
  params.append('productId', String(productId));
  params.append('userId', String(userId));
  params.append('quantity', String(quantity));

  const response = await api.post('/cartItems', params, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  });
  return response.data;
};
