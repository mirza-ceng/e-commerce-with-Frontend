import React, { useState, useEffect } from 'react';
import { useAuth } from '../services/AuthContext';
import { getOrdersByUser } from '../services/OrderService';
import { updatePassword, updateAddress } from '../services/UserService';
import './UserProfilePage.css';

const UserProfilePage: React.FC = () => {
  const { user } = useAuth();
  const [activeSection, setActiveSection] = useState<'profile' | 'orders' | 'tracking' | 'settings'>('profile');
  const [orders, setOrders] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [address, setAddress] = useState('');

  // Fetch real orders from backend
  useEffect(() => {
    if (user?.userId) {
      fetchUserOrders();
    }
  }, [user]);

  const fetchUserOrders = async () => {
    try {
      const ordersData = await getOrdersByUser(user!.userId);
      // Transform backend data to frontend format
      const transformedOrders = ordersData.map((order: any) => ({
        id: order.id,
        date: new Date(order.createdAt).toLocaleDateString(),
        totalPrice: order.totalPrice,
        status: order.status
      }));
      setOrders(transformedOrders);
    } catch (error) {
      console.error('Error fetching orders:', error);
      setOrders([]);
    } finally {
      setLoading(false);
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'PENDING': return '#ffa726';
      case 'PROCESSİNG': return '#42a5f5';
      case 'SHİPPED': return '#ab47bc';
      case 'DELİVERED': return '#66bb6a';
      case 'CANCELLED': return '#ef5350';
      default: return '#999';
    }
  };

  const handlePasswordChange = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user?.userId) {
      alert('User not authenticated!');
      return;
    }
    if (newPassword !== confirmPassword) {
      alert('New passwords do not match!');
      return;
    }

    try {
      await updatePassword(user.userId, newPassword);
      alert('Password updated successfully!');
      setCurrentPassword('');
      setNewPassword('');
      setConfirmPassword('');
    } catch (error) {
      console.error('Error updating password:', error);
      alert('Failed to update password. Please try again.');
    }
  };

  const handleAddressUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user?.userId) {
      alert('User not authenticated!');
      return;
    }

    try {
      await updateAddress(user.userId, address);
      alert('Address updated successfully!');
      setAddress('');
    } catch (error) {
      console.error('Error updating address:', error);
      alert('Failed to update address. Please try again.');
    }
  };

  const renderProfileOverview = () => (
    <div className="profile-section">
      <div className="profile-header">
        <div className="avatar">
          <span>SM</span>
        </div>
        <div className="profile-info">
          <h2>Senai Mirza</h2>
          <p>{user?.email || 'senai@example.com'}</p>
          <button className="edit-profile-btn">Edit Profile</button>
        </div>
      </div>
      <div className="profile-stats">
        <div className="stat-card">
          <h3>12</h3>
          <p>Total Orders</p>
        </div>
        <div className="stat-card">
          <h3>$1,249</h3>
          <p>Total Spent</p>
        </div>
        <div className="stat-card">
          <h3>4.8</h3>
          <p>Rating</p>
        </div>
      </div>
    </div>
  );

  const renderOrderHistory = () => (
    <div className="profile-section">
      <h3>Order History</h3>
      {loading ? (
        <div className="text-center py-4">
          <p>Loading orders...</p>
        </div>
      ) : orders.length === 0 ? (
        <div className="text-center py-4">
          <p>No orders found.</p>
        </div>
      ) : (
        <div className="orders-table">
          <table>
            <thead>
              <tr>
                <th>Order ID</th>
                <th>Date</th>
                <th>Total Price</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((order) => (
                <tr key={order.id}>
                  <td>#{order.id}</td>
                  <td>{order.date}</td>
                  <td>${order.totalPrice.toFixed(2)}</td>
                  <td>
                    <span
                      className="status-badge"
                      style={{ backgroundColor: getStatusColor(order.status) }}
                    >
                      {order.status}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );

  const renderOrderTracking = () => (
    <div className="profile-section">
      <h3>Order Tracking</h3>
      <div className="tracking-container">
        <div className="order-info">
          <h4>Order #1002</h4>
          <p>Estimated delivery: January 15, 2026</p>
        </div>
        <div className="progress-bar">
          <div className="progress-step completed">
            <div className="step-circle">✓</div>
            <p>Order Placed</p>
          </div>
          <div className="progress-line completed"></div>
          <div className="progress-step completed">
            <div className="step-circle">✓</div>
            <p>Processing</p>
          </div>
          <div className="progress-line completed"></div>
          <div className="progress-step active">
            <div className="step-circle">📦</div>
            <p>Shipped</p>
          </div>
          <div className="progress-line"></div>
          <div className="progress-step">
            <div className="step-circle">🚚</div>
            <p>Out for Delivery</p>
          </div>
          <div className="progress-line"></div>
          <div className="progress-step">
            <div className="step-circle">✓</div>
            <p>Delivered</p>
          </div>
        </div>
      </div>
    </div>
  );

  const renderAccountSettings = () => (
    <div className="profile-section">
      <h3>Account Settings</h3>
      <div className="settings-forms">
        <form onSubmit={handlePasswordChange} className="settings-form">
          <h4>Change Password</h4>
          <div className="form-group">
            <label>Current Password</label>
            <input
              type="password"
              value={currentPassword}
              onChange={(e) => setCurrentPassword(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label>New Password</label>
            <input
              type="password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label>Confirm New Password</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="save-btn">Update Password</button>
        </form>

        <form onSubmit={handleAddressUpdate} className="settings-form">
          <h4>Update Address</h4>
          <div className="form-group">
            <label>Address</label>
            <textarea
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              placeholder="Enter your address"
              rows={4}
              required
            />
          </div>
          <button type="submit" className="save-btn">Update Address</button>
        </form>
      </div>
    </div>
  );

  return (
    <div className="user-profile-page">
      <div className="sidebar">
        <div className="sidebar-header">
          <h2>My Account</h2>
        </div>
        <nav className="sidebar-nav">
          <button
            className={activeSection === 'profile' ? 'active' : ''}
            onClick={() => setActiveSection('profile')}
          >
            My Profile
          </button>
          <button
            className={activeSection === 'orders' ? 'active' : ''}
            onClick={() => setActiveSection('orders')}
          >
            My Orders
          </button>
          <button
            className={activeSection === 'tracking' ? 'active' : ''}
            onClick={() => setActiveSection('tracking')}
          >
            Order Tracking
          </button>
          <button
            className={activeSection === 'settings' ? 'active' : ''}
            onClick={() => setActiveSection('settings')}
          >
            Account Settings
          </button>
        </nav>
      </div>

      <div className="main-content">
        {activeSection === 'profile' && renderProfileOverview()}
        {activeSection === 'orders' && renderOrderHistory()}
        {activeSection === 'tracking' && renderOrderTracking()}
        {activeSection === 'settings' && renderAccountSettings()}
      </div>
    </div>
  );
};

export default UserProfilePage;
