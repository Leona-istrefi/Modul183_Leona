import React, { useEffect, useState } from 'react';
import { FaHeart, FaShoppingCart } from 'react-icons/fa';
import '../styles/molecules.css';

const Navbar = () => {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const [profilePicture, setProfilePicture] = useState<string | null>(null);

    useEffect(() => {
        if (token) {
            fetch('http://localhost:8080/profile', {
                headers: { Authorization: `Bearer ${token}` }
            })
                .then(res => res.json())
                .then(data => {
                    if (data.profilePicture) {
                        setProfilePicture(`http://localhost:8080/${data.profilePicture}`);
                    }
                })
                .catch(() => {});
        }
    }, []);

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
                        <a href="/profile" className="navbar-profile">
                            {profilePicture ? (
                                <img src={profilePicture} alt="Profil" className="navbar-profile-picture" />
                            ) : (
                                <div className="navbar-profile-placeholder">
                                    {username?.charAt(0).toUpperCase()}
                                </div>
                            )}
                        </a>
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