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

export const getCartItems = async (userId: number): Promise<CartItem[]> => {
  if (!userId || userId <= 0) {
    throw new Error('Invalid userId provided');
  }

  try {
    const response = await api.get(`/cartItems/user/${userId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching cart items:', error);
    throw error;
  }
};

export const updateCartItemQuantity = async (cartItemId: number, quantity: number): Promise<CartItem> => {
  try {
    const response = await api.put(`/cartItems/${cartItemId}`, null, { params: { quantity } });
    return response.data;
  } catch (error) {
    console.error('Error updating cart item:', error);
    throw error;
  }
};

export const deleteCartItem = async (cartItemId: number): Promise<void> => {
  try {
    await api.delete(`/cartItems/${cartItemId}`);
  } catch (error) {
    console.error('Error deleting cart item:', error);
    throw error;
  }
};
