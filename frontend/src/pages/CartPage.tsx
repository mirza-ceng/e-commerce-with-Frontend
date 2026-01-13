import React, { useEffect, useState } from 'react';
import { useAuth } from '../services/AuthContext';
import { getCartItems, updateCartItemQuantity, deleteCartItem } from '../services/CartService';
import { createOrder } from '../services/OrderService';
import { CartItem, Order } from '../types';

const CartPage: React.FC = () => {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const { user, isAuthenticated } = useAuth();

  useEffect(() => {
    console.log('CartPage useEffect - isAuthenticated:', isAuthenticated, 'user:', user);
    if (isAuthenticated && user) {
      fetchCartItems();
    } else {
      setLoading(false);
    }
  }, [isAuthenticated, user]);

  const fetchCartItems = async () => {
    console.log('fetchCartItems called');
    console.log('user object:', user);
    console.log('user.userId:', user?.userId);
    console.log('isAuthenticated:', isAuthenticated);

    if (!user || !user.userId) {
      console.error('User or userId is null/undefined');
      setError('Kullanıcı bilgileri bulunamadı');
      setLoading(false);
      return;
    }

    try {
      console.log('Fetching cart items for userId:', user.userId, 'Type:', typeof user.userId);
      const items = await getCartItems(user.userId);
      console.log('Fetched cart items:', items);
      setCartItems(items);
    } catch (err: any) {
      console.error('Error fetching cart items:', err);
      console.error('Error details:', err.response?.data || err.message);
      setError('Sepet öğeleri yüklenirken hata oluştu');
    } finally {
      setLoading(false);
    }
  };

  const handleQuantityChange = async (cartItemId: number, newQuantity: number) => {
    if (newQuantity < 1) return;

    try {
      await updateCartItemQuantity(cartItemId, newQuantity);
      // Update local state
      setCartItems(items =>
        items.map(item =>
          item.id === cartItemId ? { ...item, quantity: newQuantity } : item
        )
      );
    } catch (err) {
      console.error('Error updating quantity:', err);
      alert('Miktar güncellenirken hata oluştu');
    }
  };

  const handleDeleteItem = async (cartItemId: number) => {
    try {
      await deleteCartItem(cartItemId);
      // Update local state
      setCartItems(items => items.filter(item => item.id !== cartItemId));
    } catch (err) {
      console.error('Error deleting item:', err);
      alert('Ürün sepetten çıkarılırken hata oluştu');
    }
  };

  const handleCreateOrder = async () => {
    if (!user) return;

    try {
      const order = await createOrder(user.userId);
      alert(`Sipariş başarıyla oluşturuldu! Sipariş ID: ${order.id}`);
      // Clear cart after successful order
      setCartItems([]);
    } catch (err) {
      console.error('Error creating order:', err);
      alert('Sipariş oluşturulurken hata oluştu');
    }
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + (item.product.price * item.quantity), 0);
  };

  if (!isAuthenticated) {
    return (
      <div className="container mt-5">
        <div className="alert alert-warning">
          Sepeti görüntülemek için giriş yapmalısınız.
        </div>
      </div>
    );
  }

  if (loading) {
    return <div className="container mt-4">Sepet yükleniyor...</div>;
  }

  if (error) {
    return <div className="container mt-4 text-danger">{error}</div>;
  }

  return (
    <div className="container mt-4">
      <h2>Sepetim</h2>

      {cartItems.length === 0 ? (
        <div className="alert alert-info">
          Sepetiniz boş.
        </div>
      ) : (
        <>
          <div className="row">
            {cartItems.map((item) => (
              <div className="col-md-12 mb-3" key={item.id}>
                <div className="card">
                  <div className="card-body">
                    <div className="row align-items-center">
                      <div className="col-md-6">
                        <h5 className="card-title">{item.product.name}</h5>
                        <p className="card-text">Fiyat: ${item.product.price.toFixed(2)}</p>
                        <p className="card-text">Stok: {item.product.stock}</p>
                      </div>
                      <div className="col-md-3">
                        <div className="input-group">
                          <button
                            className="btn btn-outline-secondary"
                            type="button"
                            onClick={() => handleQuantityChange(item.id, item.quantity - 1)}
                          >
                            -
                          </button>
                          <input
                            type="number"
                            className="form-control text-center"
                            value={item.quantity}
                            onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value) || 1)}
                            min="1"
                          />
                          <button
                            className="btn btn-outline-secondary"
                            type="button"
                            onClick={() => handleQuantityChange(item.id, item.quantity + 1)}
                          >
                            +
                          </button>
                        </div>
                      </div>
                      <div className="col-md-2">
                        <p className="mb-1">Toplam: ${(item.product.price * item.quantity).toFixed(2)}</p>
                      </div>
                      <div className="col-md-1">
                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => handleDeleteItem(item.id)}
                        >
                          Sil
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>

          <div className="row mt-4">
            <div className="col-md-12">
              <div className="card">
                <div className="card-body">
                  <div className="d-flex justify-content-between align-items-center">
                    <h4>Toplam Tutar: ${calculateTotal().toFixed(2)}</h4>
                    <button
                      className="btn btn-success btn-lg"
                      onClick={handleCreateOrder}
                    >
                      Sipariş Oluştur
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default CartPage;
