import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../services/AuthContext';

const Header = () => {
    const { isAuthenticated, logout, user } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <header>
            <nav className="navbar navbar-expand-lg" style={{ backgroundColor: '#a105cc', borderRadius: '0 0 20px 20px'}}>
                <div className="container-fluid" style={{ backgroundColor: '#a105cc', borderRadius: '0 0 20px 20px' }}>
                    <Link className="navbar-brand" to="/">
                        <img src="\Ekran_görüntüsü_2026-01-15_000050-removebg-preview.png" alt="E-Commerce Logo" height="70" />
                    </Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav" >
                        <ul className="navbar-nav ms-auto">
                            <li className="nav-item">
                                <Link className="nav-link" to="/" style={{ color: '#f6f7f8' }}>Home</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/products" style={{ color: '#f6f7f8' }}>Products</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/cart" style={{ color: '#f6f7f8' }}>Cart</Link>
                            </li>
                            {isAuthenticated ? (
                                <>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/profile" style={{ color: '#f6f7f8' }}>Profile</Link>
                                    </li>
                                    <li className="nav-item">
                                        <button className="btn btn-link nav-link" onClick={handleLogout} style={{ color: '#f6f7f8' }}>Logout</button>
                                    </li>
                                </>
                            ) : (
                                <>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/login" style={{ color: '#f6f7f8' }}>Login</Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/register" style={{ color: '#f6f7f8' }}>Register</Link>
                                    </li>
                                </>
                            )}
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
    );
};

export default Header;
