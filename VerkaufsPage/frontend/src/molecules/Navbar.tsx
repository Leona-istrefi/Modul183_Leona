import React from 'react';
import { FaHeart, FaShoppingCart } from 'react-icons/fa';
import '../styles/molecules.css';

const Navbar = () => {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        window.location.href = '/login';
    };

    return (
        <nav className="navbar">
            <a href="/listings" className="navbar-brand">Kleidungsshop</a>
            <div className="navbar-links">
                <a href="/listings">Inserate</a>
                {token && (
                    <>
                        <a href="/favorites" className="navbar-heart" title="Favoriten">
                            <FaHeart />
                        </a>
                        <a href="/cart" className="navbar-heart" title="Warenkorb">
                            <FaShoppingCart />
                        </a>
                    </>
                )}
                {token ? (
                    <>
                        <a href="/profile" className="navbar-user">Eingeloggt als {username}</a>
                        <button className="navbar-logout" onClick={handleLogout}>Logout</button>
                    </>
                ) : (
                    <a href="/login">Login</a>
                )}
            </div>
        </nav>
    );
};

export default Navbar;